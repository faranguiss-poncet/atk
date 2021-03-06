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
 
// $Id$
// 
// Description:       

package fr.esrf.tangoatk.core.attribute;


import java.util.*;

import fr.esrf.tangoatk.core.*;

import fr.esrf.TangoApi.*;
import fr.esrf.Tango.AttrQuality;
import fr.esrf.Tango.DevFailed;

public class StringAttributeHelper implements java.io.Serializable
{
  IAttribute attribute;
  EventSupport propChanges;

  public StringAttributeHelper(IAttribute attribute)
  {
    init(attribute);
  }

  void init(IAttribute attribute)
  {
    setAttribute(attribute);
    propChanges = ((AAttribute) attribute).getPropChanges();
  }
  
  
  public void setAttribute(IAttribute attribute)
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


  protected StringScalarHistory[] getStringScalarAttHistory(DeviceDataHistory[] attPollHist)
  {
     List<StringScalarHistory>  hist;
     StringScalarHistory histElem;
     fr.esrf.Tango.AttrQuality attq;
     int i;

     if (attPollHist.length <= 0)
        return null;

     hist = new Vector<StringScalarHistory> ();

     for (i = 0; i < attPollHist.length; i++)
     {
	 histElem = new StringScalarHistory();
	 try
	 {
	    histElem.setTimestamp(attPollHist[i].getTime());
	 }
	 catch (Exception ex)
	 {
	    histElem.setTimestamp(0);
	 }

	 try
	 {
	    attq = attPollHist[i].getAttrQuality();
	    if (AttrQuality._ATTR_VALID == attq.value())
	    {
	       histElem.setState(IAttribute.VALID);
	    }
	    else
	    {
	       if (AttrQuality._ATTR_INVALID == attq.value())
	       {
		  histElem.setState(IAttribute.INVALID);
	       }
	       else
	       {
		  if (AttrQuality._ATTR_ALARM == attq.value())
		  {
		     histElem.setState(IAttribute.ALARM);
		  }
		  else
		  {
		     if (AttrQuality._ATTR_WARNING == attq.value())
		     {
		        histElem.setState(IAttribute.WARNING);
		     }
		     else
		     {
			if (AttrQuality._ATTR_CHANGING == attq.value())
			{
			   histElem.setState(IAttribute.CHANGING);
			}
			else
			   histElem.setState(IAttribute.UNKNOWN);
		     }
		  }
	       }
	    }
	 }
	 catch (Exception ex)
	 {
	    histElem.setState(IAttribute.UNKNOWN);
	 }

	 try
	 {
	    histElem.setValue(attPollHist[i].extractString());
	 }
	 catch (Exception ex)
	 {
	    histElem.setValue(null);
	 }
	 hist.add(i, histElem);
     }

     StringScalarHistory[] histArray;
     //histArray = (StringScalarHistory[]) hist.toArray(new StringScalarHistory[0]);
     histArray = hist.toArray(new StringScalarHistory[0]);
     return histArray;
  }
    
    

    protected IAttributeScalarHistory[] getScalarAttHistory(DeviceDataHistory[] attPollHist)
    {
       return (getStringScalarAttHistory(attPollHist));
    }


    public String getStringScalarSetPoint(DeviceAttribute attribute)
    {
	String[]  str_arr=null;
	
	try
	{
	   str_arr = attribute.extractStringArray();
	}
	catch( DevFailed e )
	{
	}
      
	if (str_arr == null)
	   return "???";
	
	if (str_arr.length < 1)
	   return "???";
	   
	if (str_arr.length > 1)
	   return str_arr[1];
	else // The attribute WRITE (WRITE ONLY) return their setPoint in the first element
	   return str_arr[0];
    }



    public String getVersion() {
	return "$Id$";
    }
}
