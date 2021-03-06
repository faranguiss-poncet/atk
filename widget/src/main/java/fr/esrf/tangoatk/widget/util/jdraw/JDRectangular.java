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
 
/**
 * User: Jean Luc
 * Date: Aug 9, 2003
 * Time: 7:04:19 PM
 */
package fr.esrf.tangoatk.widget.util.jdraw;

import java.awt.*;

/**
 * JDraw Rectanglar graphic object (All object having a rectangular sizing behavior)
 */
public abstract class JDRectangular extends JDObject {
  
  public void paint(JDrawEditor parent,Graphics g) {
    if(!visible) return;

    Graphics2D g2 = (Graphics2D) g;
    prepareRendering(g2);

    if (fillStyle != FILL_STYLE_NONE) {
      Paint p = GraphicsUtils.createPatternForFilling(this);
      if(p!=null) g2.setPaint(p);
      g.fillPolygon(ptsx, ptsy, ptsx.length);
    }

    if (lineWidth > 0) {
      g.setColor(foreground);
      BasicStroke bs = GraphicsUtils.createStrokeForLine(lineWidth, lineStyle);

      if (bs != null) {
        Stroke old = g2.getStroke();
        g2.setStroke(bs);
        g.drawPolygon(ptsx, ptsy, ptsx.length);
        g2.setStroke(old);
      } else {
        g.drawPolygon(ptsx, ptsy, ptsx.length);
      }
    }

    paintShadows(g);
  }

  int getSummitMotion(int id) {
    switch(id) {
      case 0:
      case 2:
      case 4:
      case 6:
        return JDObject.BOTH_SM;
      case 1:
      case 5:
        return JDObject.VERTICAL_SM;
    }
    return JDObject.HORIZONTAL_SM;
  }

  public void setLeft(int x) {
    int nw = getWidth() + getLeft() - x;
    summit[0].x = x;
    summit[6].x = x;
    summit[2].x = x+nw;
    summit[4].x = x+nw;
    centerSummit();
    updateShape();
  }

  public int getLeft() {
    return (int)Math.min(summit[2].x,summit[0].x);
  }

  public void setTop(int y) {
    int nh = getHeight() + getTop() - y;
    summit[0].y = y;
    summit[2].y = y;
    summit[4].y = y+nh;
    summit[6].y = y+nh;
    centerSummit();
    updateShape();
  }

  public int getTop() {
    return (int)Math.min(summit[0].y,summit[4].y);
  }

  public void setRight(int x) {
    int nw = getWidth() + x - getRight();
    summit[2].x = x;
    summit[4].x = x;
    summit[0].x = x-nw;
    summit[6].x = x-nw;
    centerSummit();
    updateShape();
  }

  public int getRight() {
    return (int)Math.max(summit[0].x,summit[2].x);
  }

  public void setBottom(int y) {
    int nh = getHeight() + y - getBottom();
    summit[0].y = y-nh;
    summit[2].y = y-nh;
    summit[4].y = y;
    summit[6].y = y;
    centerSummit();
    updateShape();
  }

  public int getBottom() {
    return (int)Math.max(summit[0].y,summit[4].y);
  }

  public void setWidth(int w) {
    int x = getLeft();
    summit[0].x = x;
    summit[6].x = x;
    summit[2].x = x + w;
    summit[4].x = x + w;
    centerSummit();
    updateShape();
  }

  public int getWidth() {
    return (int)Math.abs(summit[2].x - summit[0].x);
  }

  public void setHeight(int h) {
    int y = getTop();
    summit[0].y = y;
    summit[2].y = y;
    summit[4].y = y+h;
    summit[6].y = y+h;
    centerSummit();
    updateShape();
  }

  public int getHeight() {
    return (int)Math.abs(summit[4].y - summit[0].y);
  }

  public void moveSummit(int id,double x,double y) {

    switch (id) {
      case 0:
        if (Math.abs(summit[2].x - x) > 0.5) {
          summit[0].x = x;
          summit[6].x = x;
        }
        if (Math.abs(summit[4].y - y) > 0.5) {
          summit[0].y = y;
          summit[2].y = y;
        }
        break;

      case 1:
        if (Math.abs(summit[4].y - y) > 0.5) {
          summit[0].y = y;
          summit[2].y = y;
        }
        break;

      case 2:
        if (Math.abs(summit[0].x - x) > 0.5) {
          summit[2].x = x;
          summit[4].x = x;
        }
        if (Math.abs(summit[4].y - y) > 0.5) {
          summit[0].y = y;
          summit[2].y = y;
        }
        break;

      case 3:
        if (Math.abs(summit[0].x - x) > 0.5) {
          summit[2].x = x;
          summit[4].x = x;
        }
        break;

      case 4:
        if (Math.abs(summit[0].x - x) > 0.5) {
          summit[2].x = x;
          summit[4].x = x;
        }
        if (Math.abs(summit[0].y - y) > 0.5) {
          summit[4].y = y;
          summit[6].y = y;
        }
        break;

      case 5:
        if (Math.abs(summit[0].y - y) > 0.5) {
          summit[4].y = y;
          summit[6].y = y;
        }
        break;

      case 6:
        if (Math.abs(summit[2].x - x) > 0.5) {
          summit[0].x = x;
          summit[6].x = x;
        }
        if (Math.abs(summit[0].y - y) > 0.5) {
          summit[4].y = y;
          summit[6].y = y;
        }
        break;

      case 7:
        if (Math.abs(summit[2].x - x) > 0.5) {
          summit[0].x = x;
          summit[6].x = x;
        }
        break;

    }

    centerSummit();
    updateShape();

  }

  void centerSummit() {
    summit[1].x = (summit[0].x + summit[2].x)/2.0;
    summit[1].y = (summit[0].y + summit[2].y)/2.0;
    summit[3].x = (summit[2].x + summit[4].x)/2.0;
    summit[3].y = (summit[2].y + summit[4].y)/2.0;
    summit[5].x = (summit[6].x + summit[4].x)/2.0;
    summit[5].y = (summit[6].y + summit[4].y)/2.0;
    summit[7].x = (summit[0].x + summit[6].x)/2.0;
    summit[7].y = (summit[0].y + summit[6].y)/2.0;
  }

  void recordSummit(StringBuffer to_write, StringBuffer decal)  {

    to_write.append(decal).append("summit:");
    to_write.append(roundDouble(summit[0].x)).append(",").append(roundDouble(summit[0].y)).append(",");
    to_write.append(roundDouble(summit[4].x)).append(",").append(roundDouble(summit[4].y)).append("\n");

  }

  public void rotate90(double x,double y) {

    // Rotate summit
    Point.Double d1 = summit[0];
    Point.Double d2 = summit[1];
    for (int i = 2;i<8; i++)
      summit[i-2] = summit[i];
    summit[6]=d1;
    summit[7]=d2;
    super.rotate90(x,y);

  }

}