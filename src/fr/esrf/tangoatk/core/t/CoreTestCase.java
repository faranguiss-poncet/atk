// File:          CoreTestCase.java
// Created:       2001-10-22 11:24:28, assum
// By:            <erik@assum.net>
// Time-stamp:    <2002-07-18 14:47:7, assum>
// 
// $Id$
// 
// Description:       
package fr.esrf.tangoatk.core.t;
import junit.framework.*;
import java.lang.reflect.*;
//import org.apache.log4j.*;
import fr.esrf.tangoatk.core.*; 
public class CoreTestCase extends TestCase {
    //protected static Logger log;
    
    public CoreTestCase(String s) {
	super(s);
    }

    protected void setUp()
    {
	//BasicConfigurator.configure();
	//Logger.getDefaultHierarchy().disable("ERROR");
    }
    
    public String toString() {
	String fqname = getClass().getName();
	
	return fqname.substring(fqname.lastIndexOf(".") + 1) +"." + getName();
    }

}