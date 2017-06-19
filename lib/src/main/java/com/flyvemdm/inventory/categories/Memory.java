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

import com.flyvemdm.inventory.FILog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class get all the information of the Memory
 */
public class Memory extends Categories {


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
	private static final long serialVersionUID = -494336872000892273L;

    // Properties of this component
    private static final String DESCRIPTION = "Memory";
    private static final String MEMINFO = "/proc/meminfo";

    /**
     * This constructor load the context and the Memory information
     * @param xCtx Context where this class work
     */
	public Memory(Context xCtx) {
		super(xCtx);
		Category c = new Category("MEMORIES");

        c.put("DESCRIPTION", DESCRIPTION);

        String capacity = "";
        try {
            capacity = getCapacity();
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
        c.put("CAPACITY", capacity);

        this.add(c);
	}

    /**
     *  Get total memory of the device
     * @return String Total Memory
     */
	public String getCapacity() throws IOException {

        String capacity = "";
        FileReader fr = null;
        try {
            File f = new File(MEMINFO);
            fr = new FileReader(f);
        	BufferedReader br = new BufferedReader(fr, 8 * 1024);
        	String line;
			while ((line = br.readLine()) != null) {
        		if (line.startsWith("MemTotal")) {
                    String[] parts = line.split(":");
                    String part1 = parts[1].trim();
                    Long memory = Long.valueOf(part1.replaceAll("(.*)\\ kB", "$1"));
                    memory = memory / 1024;
                    capacity =  String.valueOf(memory);
        		}
        	}

            br.close();
        } catch (IOException e) {
            FILog.e( e.getMessage());
        } finally {
            if(fr!=null) {
                fr.close();
            }
        }
        return capacity;
	}
}
