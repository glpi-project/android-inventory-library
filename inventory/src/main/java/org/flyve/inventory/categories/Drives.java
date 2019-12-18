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
import android.os.Environment;
import android.os.StatFs;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

import java.io.BufferedReader;
import java.io.File;
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
    private final Context context;

    /**
     * This constructor load the context and load the Drivers information
     * @param xCtx Context where this class work
     */

	public Drives(Context xCtx) {
        super(xCtx);

        context = xCtx;

        this.addStorage(Environment.getRootDirectory());
        this.addStorage(Environment.getExternalStorageDirectory());
	    this.addStorage(Environment.getDataDirectory());
	    this.addStorage(Environment.getDownloadCacheDirectory());
        if (System.getenv("SECONDARY_STORAGE") != null) {
            this.addStorage(new File(System.getenv("SECONDARY_STORAGE")));
        }
    }
    
    /**
     * Add a storage to inventory
     * @param f the partition to inventory
     */
    private void addStorage(File f) {
        try {
            Category c = new Category("DRIVES", "drives");

            c.put("VOLUMN", new CategoryValue(getVolume(f), "VOLUMN", "path"));
            c.put("TOTAL", new CategoryValue(getTotal(f), "TOTAL", "total"));
            c.put("FREE", new CategoryValue(getFreeSpace(f), "FREE", "free"));
            c.put("FILESYSTEM", new CategoryValue(getFileSystem(f), "FILESYSTEM", "filesystem"));
            c.put("TYPE", new CategoryValue(getType(f), "TYPE", "type"));

            this.add(c);
        }  catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.DRIVES, ex.getMessage()));
        }
    }

    /**
     * Get the volume of the storage
     * @param f file
     * @return string with the volume
     */
    public String getVolume(File f) {
        String fileVolume = "N/A";
        try {
            Process mount = Runtime.getRuntime().exec("mount");
            BufferedReader bufferedFileInfo = new BufferedReader(new InputStreamReader(mount.getInputStream()));
            String line;
            while ((line = bufferedFileInfo.readLine()) != null) {
                String[] fileInfo = line.split("\\s+");
                if (fileInfo[1].contentEquals(f.getAbsolutePath())) {
                    fileVolume = fileInfo[0];
                    break;
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.DRIVES_VOLUME, ex.getMessage()));
        }
        return fileVolume;
    }

    /**
     * Get the total space of the drive
     * @param f file
     * @return string the total space
     */
    public String getTotal(File f) {
        String val = "N/A";
        try {
            Long total = f.getTotalSpace();
            int toMega = 1048576;
            total = total / toMega;
            val = total.toString();
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.DRIVES_TOTAL, ex.getMessage()));
        }
        return val;
    }

    /**
     * Get the free space of the drive
     * @param f file
     * @return string the free space
     */
    public String getFreeSpace(File f) {
        String value = "N/A";
        try {
            StatFs stat = new StatFs(f.getPath());
            long bytesAvailable;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
            } else {
                bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
            }
            long valueBytes = bytesAvailable / (1024 * 1024);
            value = String.valueOf(valueBytes);
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.DRIVES_FREE_SPACE, ex.getMessage()));
        }
        return value;
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
            BufferedReader bufferedFileInfo = new BufferedReader(new InputStreamReader(mount.getInputStream()));
            String line;
            while ((line = bufferedFileInfo.readLine()) != null) {
                String[] fileInfo = line.split("\\s+");
                if (fileInfo[1].contentEquals(f.getAbsolutePath())) {
                    fileSystem = fileInfo[2];
                    break;
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.DRIVES_FILE_SYSTEM, ex.getMessage()));
        }
        return fileSystem;
    }

    /**
     * Get the filesystem mount point
     * @param f file
     * @return string filesystem mount point
     */
    public String getType(File f) {
        String pointMounted = "N/A";
        try {
            Process mount = Runtime.getRuntime().exec("mount");
            BufferedReader bufferedFileInfo = new BufferedReader(new InputStreamReader(mount.getInputStream()));
            String line;
            while ((line = bufferedFileInfo.readLine()) != null) {
                String[] fileInfo = line.split("\\s+");
                if (fileInfo[1].contentEquals(f.getAbsolutePath())) {
                    pointMounted = fileInfo[1];
                    break;
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.DRIVES_TYPE, ex.getMessage()));
        }
        return pointMounted;
    }
}
