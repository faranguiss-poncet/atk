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
 
package fr.esrf.tangoatk.widget.util.interlock.shape;
/* Class generated by JDraw */

import java.awt.*;

/** ---------- NetDevice1 class ---------- */
public class NetDevice1 {

  private static int[][] xPolys = null;
  private static int[][] yPolys = null;

  private static Color sColor0 = new Color(153,153,153);

  private static int[][] xOrgPolys = {
    {-25,-18,24,26,26,21},
    {-26,-26,-24,19,21,21,19,-24},
    {-24,-24,-23,-22,-20,-19,-18,-18},
    {-16,-16,-15,-14,-12,-11,-10,-10},
    {-8,-8,-7,-6,-4,-3,-2,-2},
    {0,0,1,2,4,5,6,6},
    {10,10,11,12,16,17,18,18},
  };

  private static int[][] yOrgPolys = {
    {-3,-10,-10,-7,1,8},
    {-2,8,10,10,8,-1,-4,-4},
    {1,5,5,6,6,5,5,1},
    {1,5,5,6,6,5,5,1},
    {1,5,5,6,6,5,5,1},
    {1,5,5,6,6,5,5,1},
    {1,5,5,6,6,5,5,1},
  };

  static public void paint(Graphics g,Color backColor,int x,int y,double size) {

    // Allocate array once
    if( xPolys == null ) {
      xPolys = new int [xOrgPolys.length][];
      yPolys = new int [yOrgPolys.length][];
      for( int i=0 ; i<xOrgPolys.length ; i++ ) {
        xPolys[i] = new int [xOrgPolys[i].length];
        yPolys[i] = new int [yOrgPolys[i].length];
      }
    }

    // Scale and translate poly
    for( int i=0 ; i<xOrgPolys.length ; i++ ) {
      for( int j=0 ; j<xOrgPolys[i].length ; j++ ) {
        xPolys[i][j] = (int)((double)xOrgPolys[i][j]*size+0.5) + x;
        yPolys[i][j] = (int)((double)yOrgPolys[i][j]*size+0.5) + y;
      }
    }

    // Paint object
    g.setColor(backColor);g.fillPolygon(xPolys[0],yPolys[0],xPolys[0].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[0],yPolys[0],xPolys[0].length);
    g.setColor(backColor);g.fillPolygon(xPolys[1],yPolys[1],xPolys[1].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[1],yPolys[1],xPolys[1].length);
    g.setColor(sColor0);g.fillPolygon(xPolys[2],yPolys[2],xPolys[2].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[2],yPolys[2],xPolys[2].length);
    g.setColor(sColor0);g.fillPolygon(xPolys[3],yPolys[3],xPolys[3].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[3],yPolys[3],xPolys[3].length);
    g.setColor(sColor0);g.fillPolygon(xPolys[4],yPolys[4],xPolys[4].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[4],yPolys[4],xPolys[4].length);
    g.setColor(sColor0);g.fillPolygon(xPolys[5],yPolys[5],xPolys[5].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[5],yPolys[5],xPolys[5].length);
    g.setColor(sColor0);g.fillPolygon(xPolys[6],yPolys[6],xPolys[6].length);
    g.setColor(Color.black);g.drawPolygon(xPolys[6],yPolys[6],xPolys[6].length);
    g.setColor(Color.black);
    g.drawLine((int)(13.0*size+0.5)+x, (int)(-2.0*size+0.5)+y, (int)(16.0*size+0.5)+x, (int)(-2.0*size+0.5)+y);
    g.setColor(Color.black);
    g.drawLine((int)(8.0*size+0.5)+x, (int)(-2.0*size+0.5)+y, (int)(11.0*size+0.5)+x, (int)(-2.0*size+0.5)+y);

  }

  static public void setBoundRect(int x,int y,double size,Rectangle bound) {
    bound.setRect((int)(-26.0*size+0.5)+x,(int)(-10.0*size+0.5)+y,(int)(53.0*size+0.5),(int)(21.0*size+0.5));
  }

}

