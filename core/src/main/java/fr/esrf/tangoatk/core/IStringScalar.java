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
 
// File:          IStringScalar.java
// Created:       2002-03-21 13:32:39, assum
// By:            <erik@assum.net>
// Time-stamp:    <2002-05-22 16:4:42, assum>
// 
// $Id$
// 
// Description:       
package fr.esrf.tangoatk.core;


public interface IStringScalar extends IScalarAttribute, IString {

    public void addStringScalarListener(IStringScalarListener l);

    public void removeStringScalarListener(IStringScalarListener l);

    public void setValue(String s);

    public String getStringValue();
    
    public String getStringDeviceValue();
    
    public String getStringSetPoint();
    
    public String getStringDeviceSetPoint();
    
    public IStringScalarHistory[] getStringScalarHistory();
    
  
    public void setPossibleValues(String[]  vals);
  
    public String[] getPossibleValues();


}