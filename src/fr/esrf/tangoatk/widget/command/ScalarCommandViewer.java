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
 
package fr.esrf.tangoatk.widget.command;

/*
 * ScalarCommandViewer.java
 *
 * Created on January 16, 2002, 4:48 PM
 */

import fr.esrf.tangoatk.core.ICommand;
import java.beans.*;
/**
 *
 * @author  root
 */
public class ScalarCommandViewer extends javax.swing.JPanel 
implements IInput , PropertyChangeListener
{
    java.util.Vector<String>   inputList;
    /** Creates new form ScalarCommandViewer */

    public ScalarCommandViewer(ICommand command)
    {
	this();
	setModel(command);
    }
    
    public ScalarCommandViewer() {
	inputList = new java.util.Vector<String> (1);
        model = null;
        addPropertyChangeListener(this);
        initComponents();
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        executeButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Input");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(jLabel1, gridBagConstraints);

        jTextField1.setMinimumSize(new java.awt.Dimension(74, 17));
        jTextField1.setPreferredSize(new java.awt.Dimension(74, 17));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        add(jTextField1, gridBagConstraints);

        executeButton.setText("Execute");
        executeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                executeButtonMouseClicked(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(executeButton, gridBagConstraints);

    }//GEN-END:initComponents

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        // Add your handling code here:
        if (java.awt.event.KeyEvent.VK_ENTER == evt.getKeyCode()) {
            firePropertyChange("execute", null, null);
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    private void executeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_executeButtonMouseClicked
        // Add your handling code here:
        firePropertyChange("execute", null, jTextField1.getText());
    }//GEN-LAST:event_executeButtonMouseClicked

    

	

    public void setModel(ICommand command)
    {
        model = command;
        executeButton.setText(model.getNameSansDevice());
    }



    public ICommand getModel()
    {
	return model;
    }
    
   
    
    public void setInputEnabled(boolean b)
    {
	jTextField1.setEnabled(b);
    }



    public boolean isInputEnabled()
    {
	return jTextField1.isEnabled();
    }



    public java.util.List<String> getInput()
    {
	inputList.clear();
	if (jTextField1.getText().length() > 0)
	   inputList.add(0, jTextField1.getText());
	return inputList;
    }



    public void setInput(java.util.List l)
    {
        if  (l == null)
	{
	   jTextField1.setText("");
	   return;
        }
	
	Object  obj;
	
	obj = l.get(0);
	if (obj instanceof  String)
	   jTextField1.setText( (String) l.get(0));
    } 

    public void propertyChange(PropertyChangeEvent propertychangeevent) {
	if ("execute".equals(propertychangeevent.getPropertyName()))
	{
	    if (model == null)
		return;
	    if (model.takesInput())
	    {
	       java.util.List  inputArg;
               inputArg = getInput();
	       model.execute(inputArg);
	    }
	    else
	       model.execute();
	}
    }

/* setVisible, isVisible, setFont and getFont inherited from JPanel */

    private void serializeInit() {
	executeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                executeButtonMouseClicked(evt);
            }
        });
	jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });
    }
    private void readObject(java.io.ObjectInputStream in)
 	throws java.io.IOException, ClassNotFoundException {
 	in.defaultReadObject();
	serializeInit();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton executeButton;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    private ICommand model;
}