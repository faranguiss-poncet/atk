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
 * EnumScalarComboEditor.java
 *
 * Author:Faranguiss Poncet 2007
 *
 * A setter for an EnumScalar attribute. The enumerated strings and the corresponding
 * tango device values are handled by the core class EnumScalar and the associated
 * classes. 
 * This widget provides a way to set the EnumScalar attributes.
 */

package fr.esrf.tangoatk.widget.attribute;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


import fr.esrf.tangoatk.core.*;
import fr.esrf.tangoatk.widget.util.jdraw.JDrawable;
import java.awt.Component;
import java.awt.HeadlessException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * A class to set the value of a EnumScalar attribute by selecting the value
 * in a the list of the enumerated values.
 * 
 * @author  poncet
 */
public class EnumScalarComboEditor extends JComboBox 
                                     implements ActionListener, IEnumScalarListener, JDrawable, PropertyChangeListener
{

    private DefaultComboBoxModel     comboModel=null;
    private String                   defActionCmd="setAttActionCmd";



     /* The bean properties */
    private java.awt.Font    theFont;
    private IEnumScalar      enumModel=null;
    private String[]         defOptionList={"None"};
    private String[]         optionList={"None"};
    private boolean          hasConfirmWindow = false;
    
    protected String confirmTitle = "Confirm attribute set value";
    protected String confirmMessage = "Do you want to set the value of the attribute?\n";

    // Default constructor
    public EnumScalarComboEditor()
    {
       enumModel = null;
       theFont = new java.awt.Font("Dialog", java.awt.Font.PLAIN, 14);

       comboModel = new DefaultComboBoxModel(optionList);
       this.setModel(comboModel);
       this.setActionCommand(defActionCmd);
       this.addActionListener(this);
    }

    public boolean getHasConfirmWindow()
    {
        return hasConfirmWindow;
    }

    
    public void setHasConfirmWindow(boolean  flag)
    {
        hasConfirmWindow = flag;
    }
    
    
    public String getConfirmTitle()
    {
        return confirmTitle;
    }

    
    public void setConfirmTitle(String title)
    {
        confirmTitle = title;
    }


    
    public String getConfirmMessage()
    {
        return confirmMessage;
    }

    
    public void setConfirmMessage(String msg)
    {
        confirmMessage = msg;
    }
    
    
    

    public IEnumScalar getEnumModel()
    {
       return enumModel;
    }


    public void setEnumModel(IEnumScalar m)
    {
       String[]   valList = null;
       int        index, valListSize = 0;
       String[]   newOptions = null;
       String     strOpt;
       String     invalidOpt;
       Double     dblOpt;
       
       // Remove old registered listener
       if (enumModel != null)
       {
           enumModel.removeEnumScalarListener(this);
           enumModel = null;
           optionList = defOptionList;
           comboModel = new DefaultComboBoxModel(optionList);
           this.setModel(comboModel);
       }

       if( m==null ) return;

       if (!m.isWritable())
	 throw new IllegalArgumentException("EnumScalarComboEditor: Only accept writable attribute.");


       enumModel = m;	     
       invalidOpt = "???";
       
       // Update the comboBox model
       valList = enumModel.getSetEnumValues();
       newOptions = null;
       if (valList != null)
       {
          valListSize = valList.length;
	  if (valListSize > 0)
	  {
	     newOptions = new String[valListSize];	     
	     for (index = 0; index < valListSize; index++)
	     {		
		newOptions[index] = new String(valList[index]);
	     }
	  }
       }
       
       if (newOptions == null)
       {
	   newOptions = new String[1];
	   newOptions[0] = new String(invalidOpt + " ");
       }
       
       if (newOptions != null)
       {
          optionList = newOptions;
	  comboModel = new DefaultComboBoxModel(optionList);
	  this.setModel(comboModel);
       }
       changeCurrentSelection(-1); //No item selected

       // Register new listener
       enumModel.addEnumScalarListener(this);
            
       Property p;
       p = enumModel.getProperty("enum_label");
       if (p != null)
          p.addPresentationListener(this);

       enumModel.refresh();
    }

    // Listen on "setpoint" change
    // this is not clean yet as there is no setpointChangeListener
    // Listen on valueChange and read the Setpoint
    public void enumScalarChange(EnumScalarEvent evt)
    {
        String     set = null;
	int        currentSelection, index;

	currentSelection = this.getSelectedIndex();  
	
	if(hasFocus())
	    set = enumModel.getEnumScalarSetPointFromDevice();
	else
	    set = enumModel.getEnumScalarSetPoint();

	if ( set == null )
	{
	    try
	    {
	        changeCurrentSelection(-1); //No item selected
	    }
	    catch (IllegalArgumentException  iaex)
	    {
                System.out.println("caught exception : "+ iaex.getMessage());
	    }
	    return;
	}
	
	if (optionList == null)
	   return;
	   
	for (index = 0; index < optionList.length; index++)
	{		
	   if (set.equals(optionList[index]))
	   {
	       if (currentSelection != index)
		   changeCurrentSelection(index);
	       return;
	   }
	}
	
	// set not found in option list : perhaps set = "Not initialised"
	try
	{
	    changeCurrentSelection(-1); //No item selected
	}
	catch (IllegalArgumentException  iaex)
	{
            System.out.println("caught exception : "+ iaex.getMessage());
	}
    }

    public void stateChange(AttributeStateEvent e)
    {
    }


    public void errorChange(ErrorEvent e)
    {
	int        currentSelection, index;

	if (enumModel == null)
           return;
	 
	currentSelection = this.getSelectedIndex();  
	if (currentSelection != -1)
	{
	    try
	    {
	        changeCurrentSelection(-1); //No item selected
	    }
	    catch (IllegalArgumentException  iaex)
	    {
                System.out.println("caught exception : "+ iaex.getMessage());
	    }
	    return;
	}
    }



    // ---------------------------------------------------
    // Action listener
    // ---------------------------------------------------
    public void actionPerformed(ActionEvent e)
    {
	JComboBox        cb=null;
	String           cmdOption = null;

	if ( !(e.getActionCommand().equals(defActionCmd)) )
	{
	    return;
	}

	if (enumModel == null)
           return;

	cb = (JComboBox) e.getSource();
	cmdOption = (String) cb.getSelectedItem();

	if (cmdOption == null)
           return;
        
	if (!hasConfirmWindow)
        {
            enumModel.setEnumScalarValue(cmdOption);
            return;
        }

        // hasConfirmWindow is true
        cb.hidePopup();
        enumModel.refresh();
        int    userAnswer=JOptionPane.NO_OPTION;	
 //System.out.println("Called actionPerformed in EnumScalarComboEditor");
	try
	{
            String msg = confirmMessage + "New value = " + cmdOption + "\n";
	    userAnswer = JOptionPane.showConfirmDialog(this, msg,
                                    confirmTitle, JOptionPane.YES_NO_OPTION);
	}
	catch (HeadlessException hex) {}
        
	if (userAnswer == JOptionPane.YES_OPTION)
        {
	    enumModel.setEnumScalarValue(cmdOption);
        }
    }
    
    
    private void changeCurrentSelection(int newIndex)
    {
	disableExecution();
	setSelectedIndex(newIndex);
//System.out.println("call to setSelectedIndex("+newIndex+") passed!");	    
	repaint();
	enableExecution();
    }
   
   
    public void enableExecution()
    {
	this.setActionCommand(defActionCmd);
    }


    public void disableExecution()
    {
	this.setActionCommand("dummy");
    }

  // ------------------------------------------------------
  // Implementation of JDrawable interface
  // ------------------------------------------------------
  public void initForEditing() {
  }

  public JComponent getComponent() {
    return this;
  }

  public String getDescription(String name) {
    return "";
  }

  public String[] getExtensionList() {
    return new String[0];
  }

  public boolean setExtendedParam(String name,String value,boolean popupErr) {
    return true;
  }

  public String getExtendedParam(String name) {
    return "";
  }
  
  
  public void propertyChange(PropertyChangeEvent evt)
  {
        Property src = (Property) evt.getSource();
        if (src == null) return;
        
        if (src.getName().equalsIgnoreCase("enum_label"))
        {
            if (src instanceof StringArrayProperty)
            {
                StringArrayProperty sap = (StringArrayProperty) src;
                String[] newEnums = sap.getStringArrayValue();
                updateEnumLabels(newEnums);
            }
        }
  }

    
    private void updateEnumLabels (String[] enums)
    {
       // Update the comboBox model
       if (enums == null) return;
       if (enums.length < 1) return;
       String[] newOptions = new String[enums.length];
       
       System.arraycopy(enums, 0, newOptions, 0, enums.length);
      
       optionList = newOptions;
       comboModel = new DefaultComboBoxModel(optionList);
       this.setModel(comboModel);

       changeCurrentSelection(-1); //No item selected
    }




    public static void main(String[] args)
    {
	 final fr.esrf.tangoatk.core.AttributeList  attList = new fr.esrf.tangoatk.core.AttributeList();
	 EnumScalarComboEditor                  esce = new EnumScalarComboEditor();
	 IEntity                                ie;
	 IEnumScalar                            enumAtt;
         JFrame                                 mainFrame = null;
         
         esce.setHasConfirmWindow(true);
	 try
	 {
//            ie = attList.add("jlp/test/1/Att_six");
//            ie = attList.add("//orion:10000/sy/ps-rips-master/plc/DipoleGrid");
            ie = attList.add("//acudebian7:10000/test/universal/1/DevFloatRO_quality_");

	    if (ie instanceof IEnumScalar)
	    {
	       enumAtt = (IEnumScalar) ie;
            System.out.println("Is an IEnumScalar!");	    
	    }
	    else
	       enumAtt = null;

	    if (enumAtt == null)
	       System.exit(-1);

	    esce.setEnumModel(enumAtt);
	 } 
	 catch (Exception e)
	 {
            System.out.println("caught exception : "+ e.getMessage());
	    System.exit(-1);
	 }
	 
	 attList.startRefresher();

	 mainFrame = new JFrame();
	 mainFrame.setContentPane(esce);
	 mainFrame.pack();

	 mainFrame.setVisible(true);


    } // end of main ()


}
