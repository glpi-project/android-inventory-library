/**
 *  LICENSE
 *
 *  This file is part of Flyve MDM Inventory Library for Android.
 * 
 *  Inventory Library for Android is a subproject of Flyve MDM.
 *  Flyve MDM is a mobile device management software.
 *
 *  Flyve MDM is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version.
 *
 *  Flyve MDM is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  ---------------------------------------------------------------------
 *  @copyright Copyright © 2018 Teclib. All rights reserved.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      https://flyve-mdm.com
 *  @link      http://flyve.org/android-inventory-library
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

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
    private final Context context;

    /**
     * This constructor load the context and the Java Virtual Machine information
     * @param xCtx Context where this class work
     */
	public Jvm(Context xCtx) {
        super(xCtx);

        context = xCtx;

        try {

            Category c = new Category("JVMS", "jvms");
            Properties props = System.getProperties();

            c.put("NAME", new CategoryValue(getName(props), "NAME", "name"));
            c.put("LANGUAGE", new CategoryValue(getLanguage(props), "LANGUAGE", "language"));
            c.put("VENDOR", new CategoryValue(getVendor(props), "VENDOR", "vendor"));
            c.put("RUNTIME", new CategoryValue(getRuntime(props), "RUNTIME", "runtime"));
            c.put("HOME", new CategoryValue(getHome(props), "HOME", "home"));
            c.put("VERSION", new CategoryValue(getVersion(props), "VERSION", "version"));
            c.put("CLASSPATH", new CategoryValue(getClasspath(props), "CLASSPATH", "classPath"));

            this.add(c);
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM, ex.getMessage()));
        }
    }

    /**
     * Get the name of the Java Virtual Machine (JVM)
     * @param props Properties
     * @return string the JVM implementation name
     */
    public String getName(Properties props) {
        String value = "N/A";
        try {
            value = props.getProperty("java.vm.name");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_NAME, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the vendor of the JVM
     * @param props Properties
     * @return string the JVM vendor
     */
    public String getVendor(Properties props) {
        String value  = "N/A";
        try {
            value = props.getProperty("java.vm.vendor");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_VENDOR, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the language
     * @param props Properties
     * @return string the JVM locale language
     */
    public String getLanguage(Properties props) {
        String language = "N/A";
        try {
            language = props.getProperty("user.language");
            language += "_";
            language += props.getProperty("user.region");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_LANGUAGE, ex.getMessage()));
        }
        return language;
    }

    /**
     * Get the runtime version
     * @param props Properties
     * @return string the java runtime version
     */
    public String getRuntime(Properties props) {
        String value = "N/A";
        try {
            value = props.getProperty("java.runtime.version");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_RUNTIME, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the java directory
     * @param props Properties
     * @return string the installation directory
     */
    public String getHome(Properties props) {
        String value = "N/A";
        try {
            value = props.getProperty("java.home");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_HOME, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the JVM version
     * @param props Properties
     * @return string the JVM implementation version
     */
    public String getVersion(Properties props) {
        String value = "N/A";
        try {
            value = props.getProperty("java.vm.version");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_VERSION, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the java class path
     * @param props Properties
     * @return string the class path
     */
    public String getClasspath(Properties props) {
        String value = "N/A";
        try {
            value = props.getProperty("java.class.path");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.JVM_CLASS_PATH, ex.getMessage()));
        }
        return value;
    }
}
