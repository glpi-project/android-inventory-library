/**
 *
 * Copyright 2017 Teclib.
 * Copyright 2010-2016 by the FusionInventory Development
 *
 * http://www.fusioninventory.org/
 * https://github.com/fusioninventory/fusioninventory-android
 *
 * ------------------------------------------------------------------------
 *
 * LICENSE
 *
 * This file is part of FusionInventory project.
 *
 * FusionInventory is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FusionInventory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @update    07/06/2017
 * @license   GPLv2 https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * @link      https://github.com/fusioninventory/fusioninventory-android
 * @link      http://www.fusioninventory.org/
 * ------------------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;

import org.flyve.inventory.FILog;

import java.util.Properties;

/**
 * This class get all the information of the Java Virtual Machine
 */
public class Jvm extends Categories {

    /*
     * The serialization runtime associates with each serializable class a version number,
     * called a serialVersionUID, which is used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object that are compatible
     * with respect to serialization. If the receiver has loaded a class for the object that has a
     * different serialVersionUID than that of the corresponding sender's class, then deserialization
     * will result in an  InvalidClassException
     *
     *  from: https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     */
	private static final long serialVersionUID = 3291981487537599599L;

    /**
     * This constructor load the context and the Java Virtual Machine information
     * @param xCtx Context where this class work
     */
	public Jvm(Context xCtx) {
        super(xCtx);

        try {
            Category c = new Category("JVMS", "jvms");
            Properties props = System.getProperties();

            c.put("NAME", new CategoryValue(getName(props), "NAME", "name"));
            c.put("LANGUAGE", new CategoryValue(getLanguage(props), "LANGUAGE", "language"));
            c.put("VENDOR", new CategoryValue(getVendor(props), "VENDOR", "vendor"));
            c.put("RUNTIME", new CategoryValue(getRuntime(props), "RUNTIME", "runtime"));
            c.put("HOME", new CategoryValue(getHome(props), "HOME", "home"));
            c.put("VERSION", new CategoryValue(getVersion(props), "VERSION", "version"));
            c.put("CLASSPATH", new CategoryValue(getmClasspath(props), "CLASSPATH", "classPath"));

            this.add(c);
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }

    /**
     * Get the name of the Java Virtual Machine (JVM)
     * @param props
     * @return string the JVM implementation name
     */
    public String getName(Properties props) {
        return props.getProperty("java.vm.name");
    }

    /**
     * Get the vendor of the JVM
     * @param props
     * @return string the JVM vendor
     */
    public String getVendor(Properties props) {
        return props.getProperty("java.vm.vendor");
    }

    /**
     * Get the language
     * @param props
     * @return string the JVM locale language
     */
    public String getLanguage(Properties props) {

        String language = props.getProperty("user.language");
        language += "_";
        language += props.getProperty("user.region");

        return language;
    }

    /**
     * Get the runtime version
     * @param props
     * @return string the java runtime version
     */
    public String getRuntime(Properties props) {
        return props.getProperty("java.runtime.version");
    }

    /**
     * Get the java directory
     * @param props
     * @return string the installation directory
     */
    public String getHome(Properties props) {
        return props.getProperty("java.home");
    }

    /**
     * Get the JVM version
     * @param props
     * @return string the JVM implementation version
     */
    public String getVersion(Properties props) {
        return props.getProperty("java.vm.version");
    }

    /**
     * Get the java class path
     * @param props
     * @return string the class path
     */
    public String getmClasspath(Properties props) {
        return props.getProperty("java.class.path");
    }
}
