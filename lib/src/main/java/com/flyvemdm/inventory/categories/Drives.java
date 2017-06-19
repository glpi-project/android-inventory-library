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
import android.os.Environment;

import com.flyvemdm.inventory.FILog;

import java.io.File;

/**
 * This class get all the information of the Drives
 */
public class Drives extends Categories {


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
	private static final long serialVersionUID = 6073387379988815108L;

    // Properties of this component
    private int toMega = 1048576;

    /**
     * This constructor load the context and load the Drivers information
     * @param xCtx Context where this class work
     */

	public Drives(Context xCtx) {
        super(xCtx);
        
        this.addStorage(xCtx, Environment.getRootDirectory());
        this.addStorage(xCtx, Environment.getExternalStorageDirectory());
	    this.addStorage(xCtx, Environment.getDataDirectory());
	    this.addStorage(xCtx, Environment.getDownloadCacheDirectory());
    }
    
    /**
     * Add a storage to inventory
     * @param xCtx the Context
     * @param f the partition to inventory
     */
    private void addStorage(Context xCtx, File f) {

    	Category c = new Category(xCtx, "DRIVES");

        c.put("VOLUMN", getVolumn(f));
        c.put("TOTAL", getTotal(f));
        c.put("FREE", getFree(f));

        this.add(c);
    }

    public String getVolumn(File f) {
        // Size of storage
        String val = "";

        try {
            val = f.toString();
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

        return val;
    }

    public String getTotal(File f) {
        String val = "";

        try {
            Long total = f.getTotalSpace();
            total = total / toMega;
            val = total.toString();
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

        return val;
    }

    public String getFree(File f) {
        String val = "";
        try {
            Long free = f.getFreeSpace();
            free = free / toMega;
            val = free.toString();
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

        return val;
    }
}
