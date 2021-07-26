package com.jeta.forms.support;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * AbeilleForms.java is an automatically generated java file. If you want to
 * make changes, you must edit the AbeilleForms.txt file. The build script will
 * read the file and generate the corresponding Java File
 *
 * Some global constants and declarations for this application.
 *
 * @author Jeff Tassin
 * @author Khanh Nguyen
 */
public class AbeilleForms {

    /**
     * @return a version string for the application
     * @throws IOException
     */
    public static String getVersion() {
        URLClassLoader cl = (URLClassLoader) AbeilleForms.class.getClassLoader();
        URL url = cl.findResource("META-INF/MANIFEST.MF");
        Manifest manifest = null;
        try {
            manifest = new Manifest(url.openStream());
        } catch (IOException e) {
            return "SNAPSHOT";
        }
        Attributes attr = manifest.getMainAttributes();

        return attr.getValue("Implementation-Version");
    }

    /**
     * @return a version string for the application
     */
    public static String getVersionEx() {
        return getVersion();
    }

    public static String getAbeilleTitle() {
        return "Abeille Forms Designer " + getVersion();
    }

}