package fr.esrf.tangoatk.widget.util.chart.examples;/*
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
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import fr.esrf.tangoatk.widget.util.chart.*;


public class ChartExample1 extends JFrame {

  
  public ChartExample1() {
  
    final JLChart chart = new JLChart();
    final JLDataView[] v = new JLDataView[32];
    final JLDataView alarm;
    final JLDataView fault;

    // Initialise chart properties
    chart.setHeaderFont(new Font("Times", Font.BOLD, 18));
    chart.setHeader("BarChart");
    chart.setLabelVisible(false);

    // Initialise axis properties
    chart.getY1Axis().setName("uSv");
    chart.getY1Axis().setAutoScale(true);
    chart.getY1Axis().setGridVisible(true);
    chart.getY1Axis().setSubGridVisible(false);

    chart.getXAxis().setAutoScale(false);
    chart.getXAxis().setMinimum(0.0);
    chart.getXAxis().setMaximum(33.0);
    chart.getXAxis().setAnnotation(JLAxis.VALUE_ANNO);
    chart.getXAxis().setName("Cells");
    chart.getXAxis().setGridVisible(false);
    chart.getXAxis().setSubGridVisible(false);

    // Build dataviews

    // Alarm and Fault level line
        
    alarm=new JLDataView();
    alarm.setName("Alarm level");
    alarm.setUnit("uSv");
    alarm.setColor(Color.orange);
    alarm.setLineWidth(2);
    alarm.add(-1.0,25.0);
    alarm.add(35.0,25.0);
    chart.getY1Axis().addDataView(alarm);
    
    fault=new JLDataView();
    fault.setName("Fault level");
    fault.setUnit("uSv");
    fault.setColor(Color.red);
    fault.setLineWidth(2);
    fault.add(-1.0,45.0);
    fault.add(35.0,45.0);    
    chart.getY1Axis().addDataView(fault);

    // To allow a diffenrent filling color for each bar,
    // we use 1 dataview per bar
    
    for(int i=0;i<32;i++) {
    
      v[i]=new JLDataView();
      v[i].setName("Cell " + Integer.toString(i+1) );
      v[i].setUnit("uSv");
      v[i].setColor(Color.black);
      v[i].setLineWidth(1);
      v[i].setBarWidth(16);
      v[i].setFillStyle(JLDataView.FILL_STYLE_SOLID);
      v[i].setViewType(JLDataView.TYPE_BAR);
      
      double val=Math.random()*50.0;
      v[i].add((double)(i+1),val);
      
      if( val>45.0 )     v[i].setFillColor(Color.red);
      else if (val>25.0) v[i].setFillColor(Color.orange);
      else               v[i].setFillColor(Color.green);      
       
      chart.getY1Axis().addDataView(v[i]);
    
    }
    
    // GUI    
    
    JPanel bot = new JPanel();
    bot.setLayout(new FlowLayout());

    JButton b = new JButton("Exit");
    b.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
    });

    bot.add(b);

    JButton c = new JButton("Graph options");
    c.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        chart.showOptionDialog();
      }
    });

    bot.add(c);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(chart, BorderLayout.CENTER);
    getContentPane().add(bot, BorderLayout.SOUTH);

    setSize(640,480);
    setTitle("Chart Example 1");
    setVisible(true);
  
  }
      

  public static void main(String[] args) {
    final ChartExample1 f = new ChartExample1();
  }

  
}
