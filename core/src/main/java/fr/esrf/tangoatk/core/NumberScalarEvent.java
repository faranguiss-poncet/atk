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
 
// File:          NumberScalarEvent.java
// Created:       2002-01-31 17:40:42, assum
// By:            <assum@esrf.fr>
// Time-stamp:    <2002-02-27 11:46:1, assum>
// 
// $Id$
// 
// Description:       
package fr.esrf.tangoatk.core;

public class NumberScalarEvent extends ATKEvent {
    double           value;
    double           devValue;
    long             timeStamp;
    INumberScalar    _source;

    //public NumberScalarEvent(Object o) {
    //     super(o, System.currentTimeMillis());
    // }
    
    public NumberScalarEvent(INumberScalar source, double value,
			     long timeStamp) {
	super(source, timeStamp);
        _source = source;
	setValue(value);
    }


    // Return the value in the display unit
    public double getValue() {
	return value;
    }


    // Return the value in the device server unit
    public double getDeviceValue()
    {
	double      dispUnitFactor = 1.0;
	
	
	if (source != null)
	   if (source instanceof INumberScalar)
	      dispUnitFactor = ((INumberScalar) source).getDisplayUnitFactor();
	      
	if (dispUnitFactor <= 0)
	   dispUnitFactor = 1.0;
	   
	   
	if (dispUnitFactor == 1.0)
	   return value;
	   
        devValue = value / dispUnitFactor; //return the value in the device server unit
	return devValue;
    }


    // Return the value in the standard unit
    public double getStandardValue()
    {
	double  devVal;
        double  stdVal;
        double  stdUnitFactor = 1.0;
	
        devVal = getDeviceValue(); // First get the value in the device server unit
	
	if (source != null)
	   if (source instanceof INumberImage)
	      stdUnitFactor = ((INumberImage) source).getStandardUnitFactor();
	      
	if (stdUnitFactor <= 0)
	   stdUnitFactor = 1.0;
	   	   
	if (stdUnitFactor == 1.0)
	   return devVal;
	   
        stdVal = devVal * stdUnitFactor; //return the value in the standard unit
	return stdVal;
    }
    

    public void setValue(double value) {
	this.value = value;
    }

    public void setSource(INumberScalar source) {
	this.source = source;
	_source = source;
    }

    public INumberScalar getNumberSource() {
	return _source;
    }

    public String getVersion() {
	return "$Id$";
    }
}
