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
import java.io.RandomAccessFile;

/**
 * This class get all the information of the Cpus
 */
public class Cpus extends Categories {

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
    private static final long serialVersionUID = 4846706700566208666L;

    /**
     * This constructor trigger get all the information about Cpus
     * @param xCtx Context where this class work
     */
    public Cpus(Context xCtx) {
        super(xCtx);

        Category c = new Category(mCtx, "CPUS");

        // Cpu Name
        c.put("NAME", getCpuName());

        // Cpu Frequency
        c.put("SPEED", getCpuFrequency());

        this.add(c);
    }

    /**
     * Get the CPU Name
     * @return String with the name
     */
    public String getCpuName() {
        String cpuname = "";

        try {
            File f = new File("/proc/cpuinfo");

            BufferedReader br = new BufferedReader(new FileReader(f), 8 * 1024);
            String infos = br.readLine();
            cpuname = infos.replaceAll("(.*):\\ (.*)", "$2");
            br.close();
        } catch (IOException e) {
            FILog.e(e.getMessage());
        }
    	return cpuname;
    }

    /**
     * Get the CPU Frequency
     * @return String with the Cpu Frequency
     */
    public String getCpuFrequency() {
        String cpuFrequency = "";

        try {
            RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            cpuFrequency = String.valueOf(Integer.valueOf(reader.readLine()) / 1000);
            reader.close();
        } catch (IOException e) {
            FILog.e(e.getMessage());
        }
    	return cpuFrequency;
    }
}
