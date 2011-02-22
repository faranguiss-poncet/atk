/*
 * ArrayInput.java
 *
 * Created on June 3, 2002, 4:49 PM
 */

package fr.esrf.tangoatk.widget.command;
import fr.esrf.tangoatk.core.ICommand;
/**
 *
 * @author  root
 */
public class ArrayInput extends javax.swing.JPanel implements IInput {

    /** Creates new form ArrayInput */
    public ArrayInput() {
        initComponents();
    }

    public ArrayInput(ICommand command) {
        this();
        setModel(command);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
          jScrollPane1 = new javax.swing.JScrollPane();
          jTable1 = new javax.swing.JTable();
          
          setLayout(new java.awt.BorderLayout());
          
          jScrollPane1.setVerticalScrollBarPolicy(20);
          jTable1.setModel(commandInputAdapter);
          jScrollPane1.setViewportView(jTable1);
          
          add(jScrollPane1, java.awt.BorderLayout.NORTH);
        
    }//GEN-END:initComponents

    public ICommand getModel() {
        return model;
    }    

    public void setModel(ICommand model) {
       this.model = model;
       commandInputAdapter.setModel(model);
    }    
    public static void main(String arg[]) {
        javax.swing.JFrame f = new javax.swing.JFrame();
        ArrayInput input = new ArrayInput();
        f.setContentPane(input);
        f.pack();
        f.show();
    }
    
    private CommandInputAdapter commandInputAdapter = new CommandInputAdapter();
    private ICommand model;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}