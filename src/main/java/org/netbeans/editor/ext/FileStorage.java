/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2000 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.editor.ext;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.netbeans.editor.Analyzer;

/**
 * Management of storage of the data for the java completion.
 * 
 * @author Miloslav Metelka, Martin Roskanin
 * @version 1.00
 */

public class FileStorage {

	/**
	 * Constant for checking the maximum size of the string. If the string size
	 * exceeds this value the error is thrown as there's very likely corruption
	 * of the file.
	 */
	private static final int MAX_STRING = 60000;

	private static final int BYTES_INCREMENT = 2048;

	private static final byte[] EMPTY_BYTES = new byte[0];

	File file;

	Thread currentLock;

	boolean openedForWrite;

	public boolean fileNotFound = false;

	/** File descriptor */
	RandomAccessFile raf;

	/** Current offset in the bytes array */
	int offset;

	/** Byte array holding the data that were read from file */
	byte[] bytes = EMPTY_BYTES;

	/** Shared char array to use for reading strings */
	char[] chars = Analyzer.EMPTY_CHAR_ARRAY;

	/** String cache */
	StringCache strCache;

	/** How many times current writer requested writing */
	private int lockDeep;

	/** file unlock without previous file lock */
	private static final String WRITE_LOCK_MISSING = "Unlock file without previous lock file"; // NOI18N

	/** Version of read database file */
	private int version = 1; // set to default version

	/**
	 * 7th bit 1 - more bytes were used for encoding of the int value 0 - only
	 * one byte has been used. The int value is less than 128.
	 */
	private static final int BIT7 = (1 << 7);

	/**
	 * 5th and 6th bit 6th | 5th 0 | 0 - 1 byte will succed 0 | 1 - 2 bytes will
	 * succed 1 | 0 - 3 bytes will succed 1 | 1 - 4 bytes will succed
	 */
	private static final int BIT6 = (1 << 6);
	private static final int BIT5 = (1 << 5);

	/**
	 * @param fileName
	 *            name of file to operate over
	 */
	public FileStorage(String fileName) {
		this(fileName, new StringCache());
	}

	public FileStorage(String fileName, StringCache strCache) {
		file = new File(fileName);
		this.strCache = strCache;
	}

	/** Setter for version of parser DB file. */
	public void setVersion(int ver) {
		version = ver;
	}

	public void open(boolean requestWrite) throws IOException {
		if (raf != null) {
			if (openedForWrite == requestWrite) {
				raf.seek(file.length());
				return; // already opened with correct type
			}
			else { // opened with different type
				close();
			}
		}

		if (requestWrite) { // check existency
			if (!file.exists()) {
				file.createNewFile();
			}
		}

		// open the file
		raf = new RandomAccessFile(file, requestWrite ? "rw" : "r"); // NOI18N
		raf.seek(file.length());
		openedForWrite = requestWrite;
		offset = 0;
	}

	public void close() throws IOException {
		if (raf != null) {
			raf.close();
			raf = null;
		}
	}

	/** Check size of bytes[] array */
	private void checkBytesSize(int len) {
		if (bytes.length < len) {
			byte[] newBytes = new byte[len + BYTES_INCREMENT];
			System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
			bytes = newBytes;
		}
	}

	/**
	 * Read some part of the file into the begining of bytes array and reset
	 * offset to zero.
	 */
	public void read(int len) throws IOException {
		checkBytesSize(len);
		raf.readFully(bytes, 0, len);
		offset = 0;
	}

	/** Write bytes array (with offset length) to the file */
	public void write() throws IOException {
		if (offset > 0) {
			raf.write(bytes, 0, offset);
		}
		offset = 0;
	}

	public void seek(int filePointer) throws IOException {
		raf.seek(filePointer);
	}

	public String getFileName() {
		return file.getAbsolutePath();
	}

	public int getFilePointer() throws IOException {
		return (int) raf.getFilePointer();
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getOffset() {
		return offset;
	}

	public int getFileLength() throws IOException {
		return (int) file.length();
	}

	public void resetBytes() {
		bytes = EMPTY_BYTES;
	}

	/** Reset the size of the file and set current offset to zero. */
	public void resetFile() throws IOException {
		open(true);
		offset = 0;
		raf.setLength(0);
		close();
	}

	/** Get the integer value from the bytes[] array */
	public int getInteger() {
		if (version == 1) {
			int i = bytes[offset++];
			i = (i << 8) + (bytes[offset++] & 255);
			i = (i << 8) + (bytes[offset++] & 255);
			i = (i << 8) + (bytes[offset++] & 255);
			return i;
		}

		if (version == 2) {
			return decodeInteger();
		}
		return 0;
	}

	/** Get the string value from the bytes[] array */
	public String getString() {
		int len = getInteger(); // length of string

		if (len < 0) {
			throw new RuntimeException("Consistency error: read string length=" + len); // NOI18N
		}

		if (len > MAX_STRING) {
			throw new RuntimeException("FileStorage: String len is " + len // NOI18N
					+ ". There's probably a corruption in the file '" // NOI18N
					+ getFileName() + "'."); // NOI18N
		}

		if (version == 1) {
			if (chars.length < len) { // check chars array size
				chars = new char[2 * len];
			}
			for (int i = 0; i < len; i++) {
				chars[i] = (char) ((bytes[offset] << 8) + (bytes[offset + 1] & 255));
				offset += 2;
			}

			String s = null;
			if (len >= 0) {
				if (strCache != null) {
					s = strCache.getString(chars, 0, len);
				}
				else { // no string cache
					s = new String(chars, 0, len);
				}
			}

			return s;

		}
		else if (version == 2) {
			try {
				String s = new String(bytes, offset, len, getEncoding());
				offset += len;
				return s;
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
				return "";
			} catch (ArrayIndexOutOfBoundsException ex) {
				StringBuffer sb = new StringBuffer(len);
				for (int i = 0; i < len; i++) {
					sb.append((char) bytes[offset + i]);
				}
				String st = sb.toString();

				throw new RuntimeException("Debug of #12932: If this bug occurs, please send the stacktrace as attachment to Issuezilla's #12932." + "\n"
						+ "http://www.netbeans.org/issues/show_bug.cgi?id=12932" + "\n" + "debug 2" + "\n" + "File:" + this.toString() + "\n" + "File Version:"
						+ version + "\n" + "Offest: " + offset + "\n" + "Read length: " + len + "\n" + "bytes.length: " + bytes.length + "\n" + "String:" + st
						+ "\n" + "Error:" + ex);
			}
		}
		return "";
	}

	/**
	 * Put the integer into bytes[] array. It is stored as four bytes in big
	 * endian.
	 */
	public void putInteger(int i) {
		if (version == 1) {
			checkBytesSize(offset + 4); // int size
			bytes[offset + 3] = (byte) (i & 255);
			i >>>= 8;
			bytes[offset + 2] = (byte) (i & 255);
			i >>>= 8;
			bytes[offset + 1] = (byte) (i & 255);
			i >>>= 8;
			bytes[offset] = (byte) i;
			offset += 4;
		}

		if (version == 2) {
			encodeInteger(i);
		}
	}

	/**
	 * Put the string into bytes[] array. First the length is stored by
	 * putInteger() and then all the characters as two bytes each in big endian.
	 */
	public void putString(String s) {
		if (s == null) {
			return;
		}

		if (version == 1) {
			int len = s.length();
			putInteger(len);

			if (len > 0) {
				checkBytesSize(offset + len * 2);
				for (int i = 0; i < len; i++) {
					char ch = s.charAt(i);
					bytes[offset + 1] = (byte) (ch & 255);
					ch >>>= 8;
					bytes[offset] = (byte) (ch & 255);
					offset += 2;
				}
			}
		}
		else if (version == 2) {
			/*
			 * Encode string to appropriate byte array according to the version
			 * of file
			 */
			byte encodedBytes[];
			try {
				encodedBytes = s.getBytes(getEncoding());
			} catch (java.io.UnsupportedEncodingException e) {
				return;
			}

			/* put the length of encoded byte array */
			int len = java.lang.reflect.Array.getLength(encodedBytes);
			if (len < 0) {
				return;
			}
			putInteger(len);

			checkBytesSize(offset + len);
			System.arraycopy(encodedBytes, 0, bytes, offset, len);
			offset += len;
		}
	}

	/** Returns decoded integer */
	private int decodeInteger() {
		int i = bytes[offset++] & 255;
		if ((i & BIT7) == 0) {
			return i;
		}
		int level = 1;
		if ((i & BIT6) != 0)
			level += 2;
		if ((i & BIT5) != 0)
			level += 1;
		i &= ~(BIT7 | BIT6 | BIT5); // reset first three bits.

		for (int j = 1; j <= level; j++) {
			i = (i << 8) + (bytes[offset++] & 255);
		}
		return i;
	}

	/** Encodes the given Integer */
	private void encodeInteger(int y) {
		int level = 0;

		if (y >= 536870912)
			level += 4; // 256*256*256*32
		else if (y >= 2097152)
			level += 3; // 256*256*32
		else if (y >= 8192)
			level += 2; // 256*32
		else if (y >= 128)
			level += 1; // 128

		checkBytesSize(offset + level + 1); // adjust the byte array

		for (int j = level; j > 0; j--) {
			bytes[offset + j] = (byte) (y & 255);
			y >>>= 8;
		}

		bytes[offset] = (byte) y;

		// set compression type bits.
		switch (level) {
		case 2:
			bytes[offset] |= BIT5;
			break;
		case 3:
			bytes[offset] |= BIT6;
			break;
		case 4:
			bytes[offset] |= BIT5;
			bytes[offset] |= BIT6;
			break;
		}
		if (level > 0)
			bytes[offset] |= BIT7; // Setting compression flag
		level++;
		offset += level;
	}

	/** Get encoding according to file version */
	private String getEncoding() {
		switch (version) {
		case 1:
			return "UTF-16BE"; // NOI18N
		case 2:
			return "UTF-8"; // NOI18N
		default:
			return "UTF-16BE"; // NOI18N
		}
	}

	/** Locks the file and disable other threads to write */
	public synchronized final void lockFile() {
		if ((currentLock == null) || (Thread.currentThread() != currentLock)) {
			try {
				if (currentLock == null) {
					currentLock = Thread.currentThread();
					lockDeep = 0;
				}
				else {
					wait();
				}
			} catch (InterruptedException ie) {
				throw new RuntimeException(ie.toString());
			} catch (IllegalMonitorStateException imse) {
				throw new RuntimeException(imse.toString());
			}
		}
		else { // inner locking block
			lockDeep++; // only increase write deepness
		}
	}

	/** Unlocks the file and notifies wqiting threads */
	public synchronized final void unlockFile() {
		if (Thread.currentThread() != currentLock) {
			throw new RuntimeException(WRITE_LOCK_MISSING);
		}
		if (lockDeep == 0) { // most outer locking block
			resetBytes();
			notify();
			currentLock = null;
		}
		else { // just inner locking block
			lockDeep--;
		}
	}

	/** Returns name of the file */
	public String toString() {
		return file.toString();
	}

}
