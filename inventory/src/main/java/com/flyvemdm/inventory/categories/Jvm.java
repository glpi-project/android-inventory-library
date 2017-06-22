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

package com.flyvemdm.inventory.categories;

import android.content.Context;

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

        Category c = new Category("JVMS");
        Properties props = System.getProperties();

            c.put("NAME", getName(props));
            c.put("LANGUAGE", getLanguage(props));
            c.put("VENDOR", getVendor(props));
            c.put("RUNTIME", getRuntime(props));
            c.put("HOME", getHome(props));
            c.put("VERSION", getVersion(props));
            c.put("CLASSPATH", getmClasspath(props));

        this.add(c);
    }

    public String getName(Properties props) {
        return props.getProperty("java.vm.name");
    }

    public String getVendor(Properties props) {
        return props.getProperty("java.vm.vendor");
    }

    public String getLanguage(Properties props) {

        String language = props.getProperty("user.language");
        language += "_";
        language += props.getProperty("user.region");

        return language;
    }

    public String getRuntime(Properties props) {
        return props.getProperty("java.runtime.version");
    }

    public String getHome(Properties props) {
        return props.getProperty("java.home");
    }

    public String getVersion(Properties props) {
        return props.getProperty("java.vm.version");
    }

    public String getmClasspath(Properties props) {
        return props.getProperty("java.class.path");
    }
}
