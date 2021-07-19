/*
 * Copyright (C) 2005 Jeff Tassin
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */


package com.jeta.swingbuilder.gui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jeta.open.gui.framework.JETADialog;
import com.jeta.open.gui.framework.JETAPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import com.jeta.swingbuilder.gui.utils.FormDesignerUtils;

/**
 * Displays a nag screen when in evalation mode.
 * @author Jeff Tassin
 */
public class NagScreen extends JETADialog
{
   private boolean m_confirmed = false;
   private boolean m_agreed = false;

   public NagScreen()
   {
      super( (javax.swing.JFrame)null, true );
      setPrimaryPanel( buildPanel() );
      setSize( getPreferredSize() );
      getOkButton().setVisible( false );
      getCloseButton().setEnabled( false );
      getCloseButton().setText( "Close" );
      setTitle( "Evaluation Version" );
   }

   public void cmdCancel()
   {
      if ( m_confirmed )
	 super.cmdCancel();
   }

   boolean isLicenseAgreed()
   {
      return m_agreed;
   }

   private JETAPanel buildPanel()
   {
      FormLayout layout = new FormLayout( "d:grow", "pref,4dlu,fill:70dlu:grow,4dlu,pref" );
      JETAPanel panel = new JETAPanel();

      panel.setLayout( layout );
      panel.setBorder( javax.swing.BorderFactory.createEmptyBorder(10,10,10,10) );

      JLabel label = new JLabel( "Evaluation Version" );
      JTextArea ta = new JTextArea();
      ta.setLineWrap( true );
      ta.setWrapStyleWord( true );
      ta.setText( "Running evaluation version of Abeille Forms Designer.  You may use this evaluation for a period of 30 days.  After which you must either purchase a license or remove this software from your computer.\n\nOnly licensed users may use and/or distribute forms created by this product.  By using this product you agree to the Abeille Forms Designer licensing terms.\n\nThis message does not appear in licensed versions of this product." );
      ta.setCaretPosition(0);

      CellConstraints cc = new CellConstraints();
      
      panel.add( label, cc.xy( 1,1 ) );
      JScrollPane scroll = new JScrollPane( ta );
      scroll.setPreferredSize( new Dimension( 50, 50 ) );
      panel.add( scroll, cc.xy( 1, 3 ) );


      JETAPanel cpanel = new JETAPanel();
      layout = new FormLayout( "pref,8dlu,pref", "pref" );
      final JRadioButton agree_radio = new JRadioButton( "I agree to the license terms" );
      final JRadioButton noagree_radio = new JRadioButton( "I don't agree to the license terms" );

      ButtonGroup grp = new ButtonGroup();
      grp.add( agree_radio );
      grp.add( noagree_radio );

      cpanel.add( agree_radio, cc.xy( 1, 1 ) );
      agree_radio.addActionListener( new ActionListener()
	 {
	    public void actionPerformed( ActionEvent evt )
	    {
	       m_confirmed = (agree_radio.isSelected() || noagree_radio.isSelected() );
	       getCloseButton().setEnabled( m_confirmed );
	       m_agreed = true;

	    }
	 });

      cpanel.add( noagree_radio, cc.xy( 3, 1 ) );
      noagree_radio.addActionListener( new ActionListener()
	 {
	    public void actionPerformed( ActionEvent evt )
	    {
	       m_confirmed = (agree_radio.isSelected() || noagree_radio.isSelected() );
	       getCloseButton().setEnabled( m_confirmed );
	       m_agreed = false;
	    }
	 });

      panel.add( cpanel, cc.xy( 1, 5 ) );
      return panel;
   }
}
