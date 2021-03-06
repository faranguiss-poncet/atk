/*
 *  Copyright (C) :	2002,2003,2004,2005,2006,2007,2008,2009
 *			European Synchrotron Radiation Facility
 *			BP 220, Grenoble 38043
 *			FRANCE
 * 
 *  This file is part of Tango.
 * 
 *  Tango is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Tango is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */

// File:          NumberScalarSetPanel.java
// Created:       2007-05-11 16:35:49, poncet
// By:            <poncet@esrf.fr>
// 
// $Id$
// 
// Description:       
package fr.esrf.tangoatk.widget.attribute;


import javax.swing.*;


import fr.esrf.tangoatk.core.*;
import fr.esrf.tangoatk.widget.properties.LabelViewer;
import fr.esrf.tangoatk.widget.properties.UnitViewer;


/**
 * A NumberScalarSetPanel is a Swing JPanel which displays the "read value" of
 * the numberScalar below a NumberScalarWheelEditor. At the left side the label of
 * the attribute is displayed and at the right the unit is displayed.
 * The label and unit are optional.
 */
public class NumberScalarSetPanel extends JPanel {

  private LabelViewer attLabelViewer;
  private NumberScalarViewer attNumberScalarViewer;
  private NumberScalarWheelEditor attNumberScalarWheelEditor;
  private UnitViewer attUnitViewerUp;
  private UnitViewer attUnitViewerDown;

  private INumberScalar numberAtt = null;

  private boolean labelVisible = true;
  private boolean unitVisible = true;


  public NumberScalarSetPanel() {
    initComponents();
  }

  //override setFont()
  public void setFont(java.awt.Font font) {

    super.setFont(font);
    if (attNumberScalarWheelEditor != null) attNumberScalarWheelEditor.setFont(font);
    if (attNumberScalarViewer != null) attNumberScalarViewer.setFont(font);
    if (attLabelViewer != null) attLabelViewer.setFont(font);
    if (attUnitViewerUp != null) attUnitViewerUp.setFont(font);
    if (attUnitViewerDown != null) attUnitViewerDown.setFont(font);

  }

  //override setBackground()
  public void setBackground(java.awt.Color bg) {

    super.setBackground(bg);
    if (attNumberScalarWheelEditor != null) attNumberScalarWheelEditor.setBackground(bg);
    if (attNumberScalarViewer != null) attNumberScalarViewer.setBackground(bg);
    if (attLabelViewer != null) attLabelViewer.setBackground(bg);
    if (attUnitViewerUp != null) attUnitViewerUp.setBackground(bg);
    if (attUnitViewerDown != null) attUnitViewerDown.setBackground(bg);

  }

  public void setNumberScalarViewerBackground(java.awt.Color bg) {
    if (attNumberScalarViewer != null) attNumberScalarViewer.setBackground(bg);
  }

  public void setAttModel(INumberScalar ins) {

    if (numberAtt != null)
      clearModel();
    if (ins == null) return;
    if (!ins.isWritable()) return;

    numberAtt = ins;

    attLabelViewer.setModel(numberAtt);
    attNumberScalarViewer.setModel(numberAtt);
    attNumberScalarViewer.setToolTipText(numberAtt.getName());
    attNumberScalarWheelEditor.setModel(numberAtt);
    attUnitViewerUp.setModel(numberAtt);
    attUnitViewerDown.setModel(numberAtt);

  }

  public INumberScalar getAttModel() {
    return numberAtt;
  }

  public void setLabelVisible(boolean lv) {

    if (lv == labelVisible) return;

    labelVisible = lv;
    attLabelViewer.setVisible(labelVisible);
    revalidate();

  }

  public boolean getLabelVisible() {

    return labelVisible;

  }

  public void setUnitVisible(boolean uv) {

    if (uv == unitVisible) return;

    unitVisible = uv;
    attUnitViewerUp.setVisible(unitVisible);
    attUnitViewerDown.setVisible(unitVisible);
    revalidate();

  }

  public boolean getUnitVisible() {

    return unitVisible;

  }

  public void clearModel() {

    if (numberAtt == null) return;

    attLabelViewer.setModel(null);
    attNumberScalarViewer.setModel(null);
    attNumberScalarViewer.setToolTipText(null);
    attNumberScalarWheelEditor.setModel(null);
    attUnitViewerUp.setModel(null);
    attUnitViewerDown.setModel(null);

    numberAtt = null;

  }

  public NumberScalarViewer getViewer() {
    return attNumberScalarViewer;
  }

  public NumberScalarWheelEditor getEditor() {
    return attNumberScalarWheelEditor;
  }

  private void initComponents() {

    java.awt.GridBagConstraints gridBagConstraints;

    attNumberScalarWheelEditor = new NumberScalarWheelEditor();
    attLabelViewer = new LabelViewer();
    attNumberScalarViewer = new NumberScalarViewer();
    attUnitViewerUp = new UnitViewer();
    attUnitViewerDown = new UnitViewer();

    setLayout(new java.awt.GridBagLayout());


    attNumberScalarWheelEditor.setBackground(getBackground());
    attNumberScalarWheelEditor.setFont(getFont());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(5, 9, 0, 9);
    add(attNumberScalarWheelEditor, gridBagConstraints);

    attLabelViewer.setFont(getFont());
    attLabelViewer.setBackground(getBackground());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.insets = new java.awt.Insets(5, 9, 5, 5);
    add(attLabelViewer, gridBagConstraints);

    attNumberScalarViewer.setBackground(new java.awt.Color(212, 208, 200));
    attNumberScalarViewer.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.LOWERED));
    attNumberScalarViewer.setFont(getFont());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    add(attNumberScalarViewer, gridBagConstraints);

    attUnitViewerUp.setBackground(getBackground());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    add(attUnitViewerUp, gridBagConstraints);

    attUnitViewerDown.setBackground(getBackground());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
    add(attUnitViewerDown, gridBagConstraints);

  }

  public static void main(String[] args) {

    JFrame f = new JFrame();
    fr.esrf.tangoatk.core.AttributeList lst;
    lst = new fr.esrf.tangoatk.core.AttributeList();
    NumberScalarSetPanel nsp = new NumberScalarSetPanel();
    nsp.getViewer().setAlarmEnabled(true);
    try{
      nsp.setAttModel((fr.esrf.tangoatk.core.attribute.NumberScalar) lst.add("jlp/test/1/att_trois"));
    } catch (Exception e) {
    }
    lst.startRefresher();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(nsp);
    f.pack();
    f.setVisible(true);

  }

}
