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
 
/*
 * ArrayCommandInput.java
 *
 * Created on June 3, 2002, 4:49 PM
 */

package fr.esrf.tangoatk.widget.command;
import fr.esrf.tangoatk.core.ICommand;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author  root
 */
public class ArrayCommandInput extends javax.swing.JPanel implements IInput
{

    /** Creates new form ArrayCommandInput */
    public ArrayCommandInput() {
        initComponents();
        javax.swing.table.DefaultTableCellRenderer cellRend = new javax.swing.table.DefaultTableCellRenderer();
        cellRend.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.getColumnModel().getColumn(CommandArrayInputAdapter.INDEX_COL).setMaxWidth(20);
        jTable1.getColumnModel().getColumn(CommandArrayInputAdapter.INDEX_COL).setCellRenderer(cellRend);
    }

    public ArrayCommandInput(ICommand command) {
        setModel(command);
        initComponents();
        javax.swing.table.DefaultTableCellRenderer cellRend = new javax.swing.table.DefaultTableCellRenderer();
        cellRend.setHorizontalAlignment(SwingConstants.CENTER);
        jTable1.getColumnModel().getColumn(CommandArrayInputAdapter.INDEX_COL).setMaxWidth(20);
        jTable1.getColumnModel().getColumn(CommandArrayInputAdapter.INDEX_COL).setCellRenderer(cellRend);
    }
    
 
    private void initComponents()
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        executeButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints1;

        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setViewportView(jTable1);
	
	
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints1);
        
        executeButton.setText("execute");
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });
        jTable1.setModel(commandArrayInputAdapter);
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 0;
        add(executeButton, gridBagConstraints1);
	setMinimumSize(new Dimension(60, 17));
	setPreferredSize(new Dimension(60, 130));
        
    }

    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        firePropertyChange("execute", null, null);
    }

    

	
    public void setModel(ICommand model)
    {
       this.model = model;
       commandArrayInputAdapter.setModel(model);
    }  



    public ICommand getModel()
    {
        return model;
    }    
    
   
    
    public void setInputEnabled(boolean b)
    {
    }



    public boolean isInputEnabled()
    {
       return true;
    }



    public java.util.List getInput()
    {
	java.util.List   inStrs;
	
	inStrs = commandArrayInputAdapter.getStrs();
		  
	return inStrs;
    }



    public void setInput(java.util.List l)
    {
        return;
    }

       
          
    public static void main(String arg[]) {
        javax.swing.JFrame f = new javax.swing.JFrame();
        ArrayCommandInput input = new ArrayCommandInput();
        f.setContentPane(input);
        f.pack();
	f.setVisible(true);
    }
    
    private CommandArrayInputAdapter commandArrayInputAdapter = new CommandArrayInputAdapter();
    private ICommand model;


    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton executeButton;

    
}
