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
 
/** A JDObject browser panel */
package fr.esrf.tangoatk.widget.util.jdraw;

import fr.esrf.tangoatk.widget.util.ATKGraphicsUtils;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class JDBrowserPanel extends JPanel implements TreeSelectionListener,ActionListener {

  private JDObject[]         allObjects;
  private JDrawEditor        invoker;
  private JTree              theTree;
  private JScrollPane        treeView;
  private JDTreeNode         rootNode;
  private DefaultTreeModel   mainTreeModel;
  private JDTreeNodeRenderer treeRenderer;
  private JDTreeNode         selectedNode;
  private JPanel             btnPanel;
  private JButton            dismissBtn;

  private JTabbedPane        tabbedPane;
  private JDObjectPanel      objectPanel;
  private JDValuePanel       valuePanel;
  private JDExtensionPanel   extensionPanel;
  private JDLabelPanel          labelPanel;
  private JDLinePanel           linePanel;
  private JDPolylinePanel       polylinePanel;
  private JDEllipsePanel        ellipsePanel;
  private JDRoundRectanglePanel roundRectanglePanel;
  private JDImagePanel          imagePanel;
  private JDSwingPanel          swingPanel;
  private JDAxisPanel           axisPanel;
  private JDBarPanel            barPanel;
  private JDTitledRectPanel     titledRectPanel;
  private Component             lastSelectedPanel=null;
  private boolean               updatingProp = false;

  public JDBrowserPanel(JDObject[] obj, JDrawEditor jc) {

    setLayout(new BorderLayout());

    int i;
    allObjects = obj;
    invoker = jc;

    // Create the tree

    rootNode = new JDTreeNode();
    selectedNode = null;

    // Add all object
    for(i=0;i<allObjects.length;i++)
      rootNode.add( new JDTreeNode(allObjects[i]) );

    // Create the tree

    mainTreeModel = new DefaultTreeModel(rootNode);
    treeRenderer = new JDTreeNodeRenderer();
    theTree = new JTree(mainTreeModel);
    theTree.setCellRenderer(treeRenderer);
    theTree.setEditable(false);
    theTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    theTree.setShowsRootHandles(false);
    theTree.setBorder(BorderFactory.createLoweredBevelBorder());
    treeView = new JScrollPane(theTree);
    treeView.setMinimumSize(new Dimension(20,290));

    add(treeView,BorderLayout.CENTER);

    // Panels
    tabbedPane = new JTabbedPane();
    objectPanel = new JDObjectPanel(null, invoker, this);
    valuePanel = new JDValuePanel(null, invoker,this);
    extensionPanel = new JDExtensionPanel(null, invoker);
    labelPanel = new JDLabelPanel(null, invoker);
    linePanel = new JDLinePanel(null, invoker);
    polylinePanel = new JDPolylinePanel(null, invoker);
    ellipsePanel = new JDEllipsePanel(null, invoker);
    roundRectanglePanel = new JDRoundRectanglePanel(null, invoker);
    imagePanel = new JDImagePanel(null, invoker);
    swingPanel = new JDSwingPanel(null, invoker);
    axisPanel = new JDAxisPanel(null, invoker);
    barPanel = new JDBarPanel(null, invoker);
    titledRectPanel = new JDTitledRectPanel(null, invoker);

    tabbedPane.add(objectPanel,"Graphics");
    tabbedPane.add(valuePanel, "Value");
    tabbedPane.add(extensionPanel, "Extensions");
    tabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        if(tabbedPane.getSelectedComponent()!=null && !updatingProp) {
          lastSelectedPanel = tabbedPane.getSelectedComponent();
        }
      }
    });

    add(tabbedPane,BorderLayout.EAST);

    btnPanel = new JPanel();
    FlowLayout fl = new FlowLayout();
    fl.setAlignment(FlowLayout.RIGHT);
    btnPanel.setLayout(fl);
    dismissBtn = new JButton("Dismiss");
    dismissBtn.setFont(JDUtils.labelFont);
    dismissBtn.addActionListener(this);
    btnPanel.add(dismissBtn);

    add(btnPanel,BorderLayout.SOUTH);

  }

  public void postInit() {
    theTree.addTreeSelectionListener(this);
    theTree.setSelectionRow(1);
  }

  public void updateNode() {
    if(selectedNode!=null)
       mainTreeModel.nodeChanged(selectedNode);
  }

  public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();
    if(src==dismissBtn) {
      if(objectPanel.nameHasChanged()) {
        if( JOptionPane.showConfirmDialog(this,
            "Object name has changed but has not been applied\nDo you want to apply ?",
            "Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION ) {
          objectPanel.applyName();
        }  else {
          objectPanel.cancelNameChanged();
        }
      }
      ATKGraphicsUtils.getWindowForComponent(this).setVisible(false);
    }
  }

  // ---------------------------------------------------------
  // TreeSelection listener
  // ---------------------------------------------------------
  public void valueChanged(TreeSelectionEvent e) {
     TreePath selPath = e.getPath();
     if( selPath!=null ) {

       updatingProp = true;

       JDTreeNode n = (JDTreeNode)selPath.getLastPathComponent();
       // Allow selection into group
       invoker.unselectAll();
       invoker.selectObject(n.getObject());
       JDObject jObj = n.getObject();
       if(jObj!=null) {

         tabbedPane.removeAll();
         tabbedPane.add(objectPanel,"Graphics");

         JDObject[] objs = new JDObject[1];
         objs[0] = jObj;
         objectPanel.updatePanel(objs);
         valuePanel.updatePanel(objs);
         extensionPanel.updatePanel(objs);

         if (jObj instanceof JDLabel) {
           JDLabel[] objs2 = new JDLabel[1];
           objs2[0] = (JDLabel)jObj;
           labelPanel.updatePanel(objs2);
           tabbedPane.add(labelPanel, "Text");
         }

         if (jObj instanceof JDLine) {
           JDLine[] objs2 = new JDLine[1];
           objs2[0] = (JDLine)jObj;
           linePanel.updatePanel(objs2);
           tabbedPane.add(linePanel, "Line");
         }

         if (jObj instanceof JDPolyline) {
           JDPolyline[] objs2 = new JDPolyline[1];
           objs2[0] = (JDPolyline)jObj;
           polylinePanel.updatePanel(objs2);
           tabbedPane.add(polylinePanel, "Polyline");
         }

         if (jObj instanceof JDEllipse) {
           JDEllipse[] objs2 = new JDEllipse[1];
           objs2[0] = (JDEllipse)jObj;
           ellipsePanel.updatePanel(objs2);
           tabbedPane.add(ellipsePanel, "Ellipse");
         }

         if (jObj instanceof JDRoundRectangle) {
           JDRoundRectangle[] objs2 = new JDRoundRectangle[1];
           objs2[0] = (JDRoundRectangle)jObj;
           roundRectanglePanel.updatePanel(objs2);
           tabbedPane.add(roundRectanglePanel, "Corner");
         }

         if (jObj instanceof JDImage) {
           JDImage[] objs2 = new JDImage[1];
           objs2[0] = (JDImage)jObj;
           imagePanel.updatePanel(objs2);
           tabbedPane.add(imagePanel, "Image");
         }

         if (jObj instanceof JDSwingObject) {
           JDSwingObject[] objs2 = new JDSwingObject[1];
           objs2[0] = (JDSwingObject)jObj;
           swingPanel.updatePanel(objs2);
           tabbedPane.add(swingPanel, "Swing");
         }

         if (jObj instanceof JDAxis) {
           JDAxis[] objs2 = new JDAxis[1];
           objs2[0] = (JDAxis)jObj;
           axisPanel.updatePanel(objs2);
           tabbedPane.add(axisPanel, "Axis");
         }

         if (jObj instanceof JDBar) {
           JDBar[] objs2 = new JDBar[1];
           objs2[0] = (JDBar)jObj;
           barPanel.updatePanel(objs2);
           tabbedPane.add(barPanel, "Bar");
         }

         if (jObj instanceof JDTitledRect) {
           JDTitledRect[] objs2 = new JDTitledRect[1];
           objs2[0] = (JDTitledRect)jObj;
           titledRectPanel.updatePanel(objs2);
           tabbedPane.add(titledRectPanel, "TitledRect");
         }

         tabbedPane.add(valuePanel, "Value");
         tabbedPane.add(extensionPanel, "Extensions");

         selectedNode = n;
         String t = "Selection browser ["+jObj+" "+jObj.getName()+" selected]";
         ((JDialog)ATKGraphicsUtils.getWindowForComponent(this)).setTitle(t);

         // Reselect last panel if possible
         try {
           tabbedPane.setSelectedComponent(lastSelectedPanel);
         } catch (IllegalArgumentException ex) {}

       } else {

         objectPanel.updatePanel(null);
         valuePanel.updatePanel(null);
         extensionPanel.updatePanel(null);
         labelPanel.updatePanel(null);
         linePanel.updatePanel(null);
         polylinePanel.updatePanel(null);
         ellipsePanel.updatePanel(null);
         roundRectanglePanel.updatePanel(null);
         imagePanel.updatePanel(null);
         swingPanel.updatePanel(null);
         axisPanel.updatePanel(null);
         selectedNode = null;
         String t = "Selection browser [none selected]";
         ((JDialog)ATKGraphicsUtils.getWindowForComponent(this)).setTitle(t);

       }

       // Work around a X11 JVM bug
       tabbedPane.getSelectedComponent().setVisible(true);

       updatingProp = false;

     }
  }

}
