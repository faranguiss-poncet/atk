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
 
// File:          BooleanSpectrumHelper.java
// Created:       2005-02-03 10:45:00, poncet
// By:            <poncet@esrf.fr>
//
// $Id$
//
// Description:

package fr.esrf.tangoatk.core.attribute;

import fr.esrf.tangoatk.core.*;

import fr.esrf.Tango.DevFailed;
import fr.esrf.TangoApi.DeviceAttribute;

public class BooleanSpectrumHelper implements java.io.Serializable
{
  AAttribute attribute;
  EventSupport propChanges;

  public BooleanSpectrumHelper(AAttribute attribute)
  {
    init(attribute);
  }

  void init(AAttribute attribute)
  {
    setAttribute(attribute);
    propChanges = attribute.getPropChanges();
  }
  
  
  public void setAttribute(AAttribute attribute)
  {
    this.attribute = attribute;
  }

  public IAttribute getAttribute()
  {
    return attribute;
  }

  protected void setProperty(String name, Number value)
  {
    attribute.setProperty(name, value);
    attribute.storeConfig();
  }

  protected void setProperty(String name, Number value, boolean writable)
  {
    attribute.setProperty(name, value, writable);
  }
  
  boolean[] getBooleanSpectrumValue(DeviceAttribute da) throws DevFailed
  {
      boolean[]  tmp = da.extractBooleanArray();
      int        nbReadElements = da.getNbRead();
      
      if (nbReadElements == tmp.length)
          return tmp;
      
      boolean[]  retval = new boolean[nbReadElements];
      for (int i = 0; i < nbReadElements; i++)
      {
          retval[i] = tmp[i];
      }
      return retval;
  }
  

  boolean[] getBooleanSpectrumSetPoint(DeviceAttribute da) throws DevFailed
  {
      boolean[]  tmp = da.extractBooleanArray();
      int        nbReadElements = da.getNbRead();
      int        nbSetElements = tmp.length - nbReadElements;
      
      // The attributes WRITE (WRITE ONLY) return their setPoint in the first sequence of elements
      // In all cases when no "set" element sequence is returned, return the read elements for setPoint
      if (nbSetElements <= 0)
      {
          return getBooleanSpectrumValue(da);
      }
      else
      {
         boolean[]  retval = new boolean[nbSetElements];
         int j = 0;
         for (int i = nbReadElements; i < tmp.length; i++)
         {
             retval[j] = tmp[i];
             j++;
         }
         return retval;
      }
  }


  void fireSpectrumValueChanged(boolean[] newValue, long timeStamp)
  {
    propChanges.fireBooleanSpectrumEvent((IBooleanSpectrum) attribute,
      newValue, timeStamp);
  }

  void insert(boolean[] boolSpect)
  {
      attribute.getAttribute().insert(boolSpect,
      ((IAttribute) attribute).getXDimension(),
      ((IAttribute) attribute).getYDimension());
  }

  
  void addBooleanSpectrumListener(IBooleanSpectrumListener l)
  {
      propChanges.addBooleanSpectrumListener(l);
  }

  
  void removeBooleanSpectrumListener(IBooleanSpectrumListener l)
  {
      propChanges.removeBooleanSpectrumListener(l);
  }


  public String getVersion() {
    return "$Id$";
  }

}
