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
import android.os.Environment;
import android.os.StatFs;

import org.flyve.inventory.FILog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

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

    /**
     * This constructor load the context and load the Drivers information
     * @param xCtx Context where this class work
     */

	public Drives(Context xCtx) {
        super(xCtx);
        this.addStorage(Environment.getRootDirectory());
        this.addStorage(Environment.getExternalStorageDirectory());
	    this.addStorage(Environment.getDataDirectory());
	    this.addStorage(Environment.getDownloadCacheDirectory());
    }
    
    /**
     * Add a storage to inventory
     * @param f the partition to inventory
     */
    private void addStorage(File f) {

        try {
            Category c = new Category("DRIVES", "drives");

            c.put("VOLUMN", new CategoryValue(getVolumn(f), "VOLUMN", "path"));
            c.put("TOTAL", new CategoryValue(getTotal(f), "TOTAL", "total"));
            c.put("FREE", new CategoryValue(getFreeSpace(f), "FREE", "free"));
            c.put("FILESYSTEM", new CategoryValue(getFileSystem(f), "FILESYSTEM", "filesystem"));

            this.add(c);
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }

    /**
     * Get the volume of the storage
     * @param f file
     * @return string with the volume
     */
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

    /**
     * Get the total space of the drive
     * @param f file
     * @return string the total space
     */
    public String getTotal(File f) {
        String val = "";

        try {
            Long total = f.getTotalSpace();
            int toMega = 1048576;
            total = total / toMega;
            val = total.toString();
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

        return val;
    }

    /**
     * Get the free space of the drive
     * @param f file
     * @return string the free space
     */
    public String getFreeSpace(File f) {
        StatFs stat = new StatFs(f.getPath());
        long bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        }
        long value = bytesAvailable / (1024 * 1024);
        return String.valueOf(value);
    }

    /**
     * Get the filesystem space of the drive
     * @param f file
     * @return string the filesystem
     */
    public String getFileSystem(File f) {
        String fileSystem = "N/A";
        try {
            Process mount = Runtime.getRuntime().exec("mount");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mount.getInputStream()));
            mount.waitFor();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\s+");
                for (int i = 0; i < split.length - 1; i++) {
                    if (split[i].contentEquals(f.getAbsolutePath())) {
                        String strMount = split[i];
                        fileSystem = split[i + 1];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return fileSystem;
    }
}
