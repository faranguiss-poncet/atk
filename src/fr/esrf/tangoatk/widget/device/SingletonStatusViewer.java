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
 

package fr.esrf.tangoatk.widget.device;
import fr.esrf.tangoatk.core.Device;
/**
 *
 * @author  root
 */
public class SingletonStatusViewer extends javax.swing.JFrame
    implements IDevicePopUp {

    private static SingletonStatusViewer self;
    StatusViewer statusPanel;
    
    /** Creates new form SingletonStatus */
    private SingletonStatusViewer () {
        initComponents();
        buttonBar1.setControlee(deviceStatus);
    }

    public static SingletonStatusViewer getInstance() {
        if (self == null) {
            self = new SingletonStatusViewer();
        }
        return self;
    }
    
    public void setModel(Device d) {
	if (d == null) return;
	
        deviceStatus.setModel(d);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        buttonBar1 = new fr.esrf.tangoatk.widget.util.ButtonBar();
        deviceStatus = new fr.esrf.tangoatk.widget.device.StatusViewer();
        
        getContentPane().setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gridBagConstraints1;
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.weightx = 0.1;
        getContentPane().add(buttonBar1, gridBagConstraints1);
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.weightx = 0.1;
        gridBagConstraints1.weighty = 0.2;
        getContentPane().add(deviceStatus, gridBagConstraints1);
        
        pack();
    }//GEN-END:initComponents

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        setVisible(false);
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        new SingletonStatusViewer().setVisible(true);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fr.esrf.tangoatk.widget.util.ButtonBar buttonBar1;
    private fr.esrf.tangoatk.widget.device.StatusViewer deviceStatus;
    // End of variables declaration//GEN-END:variables

}