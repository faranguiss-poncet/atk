/*
 * AttributePropertyFrame.java
 *
 * Created on November 23, 2001, 5:48 PM
 */

package fr.esrf.tangoatk.widget.attribute;
import fr.esrf.tangoatk.core.*;
import fr.esrf.tangoatk.widget.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
/**
 *
 * @author  root
 */
public class PropertyFrame extends javax.swing.JFrame {
    IEntity model;
    boolean added = false;
 
    /** Creates new form AttributePropertyFrame */
    public PropertyFrame() {
        initComponents();



	jTabbedPane2.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent evt) {
		    tabChanged(evt);
		}
	    });
    }

    void tabChanged(ChangeEvent evt) {
	Component comp = jTabbedPane2.getSelectedComponent();
	
	if (comp == jScrollPane1 ) {
	    buttonBar1.setControlee((IControlee)propertyListViewer1.getControlee());
	    return;
	} 
	
	buttonBar1.setControlee((IControlee)comp);
	
    }
				       
    public void setEditable(boolean b) {
        propertyListViewer1.setEditable(b);
    }
    
    public boolean isEditable() {
	return propertyListViewer1.isEditable();
    }


    public void setModel(IAttribute m) {
	model = m;
	propertyListViewer1.setModel(model.getPropertyMap());
	jTabbedPane2.remove(setPanel);

	if (!(m instanceof INumberScalar)) {
	    jTabbedPane2.remove(trend);
	}

	buttonBar1.setControlee(propertyListViewer1.getControlee());
    }
    
    public void setModel(IAttributeViewer m) {
	model = m.getModel();
	propertyListViewer1.setModel(model.getPropertyMap());
        trend.setListVisible(false);
	if (m.isValueEditable() && ((IAttribute)model).isWritable()) {

	    if (model instanceof INumberScalar) {
		setPanel.setModel((INumberScalar)model);
		jTabbedPane2.add("Set value", setPanel);
	    } else if (model instanceof IStringScalar) {
		setPanel.setModel((IStringScalar)model);
		jTabbedPane2.add("Set value", setPanel);
	    } // end of else
	} 

        if (model instanceof INumberScalar) {
            trend.addAttribute((INumberScalar)model);  
        }

	buttonBar1.setControlee(propertyListViewer1.getControlee());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
            jTabbedPane2 = new javax.swing.JTabbedPane();
            jScrollPane1 = new javax.swing.JScrollPane();
            propertyListViewer1 = new fr.esrf.tangoatk.widget.properties.PropertyListViewer2();
            trend = new fr.esrf.tangoatk.widget.attribute.Trend();
            buttonBar1 = new fr.esrf.tangoatk.widget.util.ButtonBar();
            
            getContentPane().setLayout(new java.awt.GridBagLayout());
            java.awt.GridBagConstraints gridBagConstraints1;
            
            addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent evt) {
                    exitForm(evt);
                }
            });
            
            jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
            propertyListViewer1.setBackground(new java.awt.Color(204, 204, 204));
            jScrollPane1.setViewportView(propertyListViewer1);
            
            jTabbedPane2.addTab("Properties", jScrollPane1);
          
          jTabbedPane2.addTab("Trend", trend);
          
          gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.weightx = 0.1;
        gridBagConstraints1.weighty = 0.1;
        getContentPane().add(jTabbedPane2, gridBagConstraints1);
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.weightx = 0.1;
        getContentPane().add(buttonBar1, gridBagConstraints1);
        
        pack();
    }//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Add your handling code here:
    }

  
    private void addTrendButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // Add your handling code here:
    }

 
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
	setVisible(false);
    }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new PropertyFrame().show();
    }

    class SetPanel extends javax.swing.JPanel implements IApplicable {
	IStringScalar string;
	INumberScalar number;
	JTextField valueField;
	JLabel labelField;
	JLabel unitField;

	public SetPanel() {
	    valueField = new JTextField();
	    valueField.setColumns(10);
	    labelField = new JLabel();
	    unitField = new JLabel();
	    this.add(labelField);
	    this.add(valueField);
	    this.add(unitField);
	}
	
	public void ok() {
	    apply();
	    cancel();
	}

	public void apply() {
	    if (number != null) {
		try {
		    number.setNumber(new Double(valueField.getText()));	     
		} catch (NumberFormatException e) {
		    JOptionPane.showMessageDialog(this,
						  number +
						  "value should be " +
						  "a numeric value",
						  "Error",
						  JOptionPane.ERROR_MESSAGE);
		    
		} // end of try-catch
		return;
	    }
	    string.setString(valueField.getText());
	}

	public void cancel() {
	    this.getRootPane().getParent().setVisible(false);
	}

	void setUp(IAttribute scalar) {
	    labelField.setText(scalar.getLabel());
	    unitField.setText(scalar.getUnit());
	}
	
	public void setModel(INumberScalar scalar) {
	    number = scalar;
	    valueField.setText(number.getNumber().toString());
	    valueField.setHorizontalAlignment(JTextField.RIGHT);
	    setUp(number);
	}

	public void setModel(IStringScalar scalar) {

	    string = scalar;
	    valueField.setText(string.getString());
	    setUp(string);
	}

    }
    SetPanel setPanel = new SetPanel();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private fr.esrf.tangoatk.widget.properties.PropertyListViewer2 propertyListViewer1;
    private fr.esrf.tangoatk.widget.attribute.Trend trend;
    private fr.esrf.tangoatk.widget.util.ButtonBar buttonBar1;
    // End of variables declaration//GEN-END:variables

}

