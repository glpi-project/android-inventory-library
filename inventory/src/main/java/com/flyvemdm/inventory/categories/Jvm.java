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

    // Properties of this component
    private String mName = "Unknown";
    private String mVendor = "Unknown";
    private String mLanguage = "Unknown";
    private String mRuntime = "Unknown";
    private String mHome = "Unknown";
    private String mVersion = "Unknown";
    private String mClasspath = "Unknown";

    /**
     * This constructor load the context and the Java Virtual Machine information
     * @param xCtx Context where this class work
     */
	public Jvm(Context xCtx) {
        super(xCtx);

        Category c = new Category(mCtx,"JVMS");
        Properties props = System.getProperties();
        /*
        for(Object prop: props.keySet() ) {
            FILog.d(this, String.format("PROP %s = %s" , (String)prop, props.get(prop) ) , Log.VERBOSE);
        }
         */
        mName = (String)props.getProperty("java.vm.name");
        c.put("NAME", mName);

        String language = (String)props.getProperty("user.language");
        language += '_';
        language += (String)props.getProperty("user.region");
        mLanguage = language;
        c.put("LANGUAGE", mLanguage);

        mVendor = (String)props.getProperty("java.vm.vendor");
        c.put("VENDOR", mVendor);

        mRuntime = (String)props.getProperty("java.runtime.version");
        c.put("RUNTIME", mRuntime);

        mHome = (String)props.getProperty("java.home");
        c.put("HOME", mHome);

        mVersion = (String)props.getProperty("java.vm.version");
        c.put("VERSION", mVersion);

        mClasspath = (String)props.getProperty("java.class.path");
        c.put("CLASSPATH", mClasspath);
        this.add(c);
    }
}
