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
 
// File:          StringSpectrumEvent.java
// Created:       2003-12-15 11:02:42, poncet
// By:            <poncet@esrf.fr>
// 
// $Id$
// 
// Description:  

     
package fr.esrf.tangoatk.core;

public class StringSpectrumEvent extends ATKEvent
{
    String[] value;
    long timeStamp;
    
    public StringSpectrumEvent(IStringSpectrum source, String[] value, long timeStamp)
    {
	super(source, timeStamp);
	setValue(value);
    }

    public String[] getValue()
    {
	return value;
    }

    public void setValue(String[] value)
    {
	this.value = value;
    }

    public void setSource(IStringSpectrum source)
    {
	this.source = source;
    }

    public String getVersion()
    {
	return "$Id$";
    }
}
