// File:          ShortImageHelper.java
// Created:       2002-01-24 10:08:28, assum
// By:            <assum@esrf.fr>
// Time-stamp:    <2002-07-16 10:32:16, assum>
//
// $Id$
//
// Description:
package fr.esrf.tangoatk.core.attribute;

import fr.esrf.tangoatk.core.*;


import fr.esrf.Tango.*;
import fr.esrf.TangoApi.*;

import java.beans.*;

class ShortImageHelper extends ANumberImageHelper {

  public ShortImageHelper(IAttribute attribute) {
    init(attribute);
  }

  protected void insert(double[] d) {
    short[] tmp = new short[d.length];
    for (int i = 0; i < tmp.length; i++) {
      tmp[i] = new Double(d[i]).shortValue();
    }

    deviceAttribute.insert(tmp, attribute.getXDimension(),
      attribute.getYDimension());
  }


  double[][] getNumberImageValue(DeviceAttribute deviceAttribute) {

    int ydim = attribute.getYDimension();
    int xdim = attribute.getXDimension();

    if (ydim != retval.length || xdim != retval[0].length) {
      retval = new double[ydim][xdim];
    }

    // 16Bits image are usualy unsigned !!!
    short[] tmp = DevVarUShortArrayHelper.extract(deviceAttribute.getAttributeValueObject().value);

    int k = 0;
    for (int i = 0; i < ydim; i++)
      for (int j = 0; j < xdim; j++) {
        int v = tmp[k++] & 0xFFFF;
        retval[i][j] = v;
      }
    return retval;
  }

  String[][] getImageValue(DeviceAttribute deviceAttribute) {

    // 16Bits image are usualy unsigned !!!
    short[] tmp = DevVarUShortArrayHelper.extract(deviceAttribute.getAttributeValueObject().value);

    int ydim = attribute.getYDimension();
    int xdim = attribute.getXDimension();
    String[][] retval = new String[ydim][xdim];

    int k = 0;
    for (int i = 0; i < ydim; i++)
      for (int j = 0; j < xdim; j++) {
        int v = tmp[k++] & 0xFFFF;
        retval[i][j] = Integer.toString(v);
      }
    return retval;
  }

  public String getVersion() {
    return "$Id$";
  }
}
