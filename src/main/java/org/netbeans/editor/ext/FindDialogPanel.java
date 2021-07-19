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

import javax.swing.JCheckBox;

import org.netbeans.editor.LocaleSupport;
import org.netbeans.editor.SettingsNames;

/**
 * 
 * @author Miloslav Metelka, Petr Nejedly
 * @version 1.0
 */
public class FindDialogPanel extends javax.swing.JPanel {

	static final long serialVersionUID = 5048601763767383114L;

	/** Initializes the Form */
	public FindDialogPanel() {
		initComponents();
		getAccessibleContext().setAccessibleName(LocaleSupport.getString("find-title")); // NOI18N
		getAccessibleContext().setAccessibleDescription(LocaleSupport.getString("ACSD_find")); // NOI18N
		findWhat.getAccessibleContext().setAccessibleDescription(LocaleSupport.getString("ACSD_" + SettingsNames.FIND_WHAT)); // NOI18N
		replaceWith.getAccessibleContext().setAccessibleDescription(LocaleSupport.getString("ACSD_" + SettingsNames.FIND_REPLACE_WITH)); // NOI18N
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the FormEditor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		findWhatPanel = new javax.swing.JPanel();
		findWhatLabel = new javax.swing.JLabel();
		findWhat = new javax.swing.JComboBox();
		replaceWithLabel = new javax.swing.JLabel();
		replaceWith = new javax.swing.JComboBox();
		highlightSearch = createCheckBox(SettingsNames.FIND_HIGHLIGHT_SEARCH, 'H');
		incSearch = createCheckBox(SettingsNames.FIND_INC_SEARCH, 'I');
		matchCase = createCheckBox(SettingsNames.FIND_MATCH_CASE, 'C');
		smartCase = createCheckBox(SettingsNames.FIND_SMART_CASE, 'S');
		wholeWords = createCheckBox(SettingsNames.FIND_WHOLE_WORDS, 'W');
		bwdSearch = createCheckBox(SettingsNames.FIND_BACKWARD_SEARCH, 'B');
		wrapSearch = createCheckBox(SettingsNames.FIND_WRAP_SEARCH, 'p');
		regExp = createCheckBox(SettingsNames.FIND_REG_EXP, 'E');

		setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints1;

		findWhatPanel.setLayout(new java.awt.GridBagLayout());
		java.awt.GridBagConstraints gridBagConstraints2;

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.gridwidth = 3;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new java.awt.Insets(12, 0, 0, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTH;
		gridBagConstraints1.weightx = 1.0;
		add(findWhatPanel, gridBagConstraints1);

		findWhatLabel.setText(LocaleSupport.getString(SettingsNames.FIND_WHAT));
		findWhatLabel.setLabelFor(findWhat);
		findWhatLabel.setDisplayedMnemonic(LocaleSupport.getChar(SettingsNames.FIND_WHAT + "-mnemonic", 'n'));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.insets = new java.awt.Insets(0, 12, 5, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
		add(findWhatLabel, gridBagConstraints1);

		findWhat.setEditable(true);
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.gridwidth = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 9, 10);
		gridBagConstraints1.weightx = 1.0;
		add(findWhat, gridBagConstraints1);

		replaceWithLabel.setText(LocaleSupport.getString(SettingsNames.FIND_REPLACE_WITH));
		replaceWithLabel.setLabelFor(replaceWith);
		replaceWithLabel.setDisplayedMnemonic(LocaleSupport.getChar(SettingsNames.FIND_REPLACE_WITH + "-mnemonic", 'l'));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.insets = new java.awt.Insets(0, 12, 9, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
		add(replaceWithLabel, gridBagConstraints1);

		replaceWith.setEditable(true);
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 2;
		gridBagConstraints1.gridwidth = java.awt.GridBagConstraints.REMAINDER;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 9, 10);
		gridBagConstraints1.weightx = 1.0;
		add(replaceWith, gridBagConstraints1);

		highlightSearch.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 3;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 10);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(highlightSearch, gridBagConstraints1);

		incSearch.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 4;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 10);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(incSearch, gridBagConstraints1);

		matchCase.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 3;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(matchCase, gridBagConstraints1);

		smartCase.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 4;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(smartCase, gridBagConstraints1);

		wholeWords.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 5;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(wholeWords, gridBagConstraints1);

		bwdSearch.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 5;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 0, 10);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(bwdSearch, gridBagConstraints1);

		wrapSearch.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 6;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 11, 10);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints1.weighty = 1.0;
		add(wrapSearch, gridBagConstraints1);

		regExp.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.gridy = 6;
		gridBagConstraints1.insets = new java.awt.Insets(0, 11, 11, 0);
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints1.weighty = 1.0;
		add(regExp, gridBagConstraints1);

	}// GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	protected javax.swing.JPanel findWhatPanel;
	protected javax.swing.JLabel findWhatLabel;
	protected javax.swing.JComboBox findWhat;
	protected javax.swing.JLabel replaceWithLabel;
	protected javax.swing.JComboBox replaceWith;
	protected javax.swing.JCheckBox highlightSearch;
	protected javax.swing.JCheckBox incSearch;
	protected javax.swing.JCheckBox matchCase;
	protected javax.swing.JCheckBox smartCase;
	protected javax.swing.JCheckBox wholeWords;
	protected javax.swing.JCheckBox bwdSearch;
	protected javax.swing.JCheckBox wrapSearch;
	protected javax.swing.JCheckBox regExp;

	// End of variables declaration//GEN-END:variables

	private JCheckBox createCheckBox(String key, char mnemonic) {
		JCheckBox box = new JCheckBox(LocaleSupport.getString(key));
		box.setToolTipText(LocaleSupport.getString(key + "-tooltip"));
		box.setMnemonic(LocaleSupport.getChar(key + "-mnemonic", mnemonic));
		return box;
	}

}
