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
 *  @author    Rafael Hernandez - <rhernandez@teclib.com>
 *  @copyright Copyright Teclib. All rights reserved.
 *  @copyright Copyright FusionInventory.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;
import android.os.Build;

import org.flyve.inventory.FILog;
import org.flyve.inventory.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Pattern;

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
    private static final String CPUINFO = "/proc/cpuinfo";
    private static final String CPUINFO_MAX_FREQ = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
    private String cpuFamily;
    private String cpuManufacturer;

    /**
     * This constructor trigger get all the information about Cpus
     * @param xCtx Context where this class work
     */
    public Cpus(Context xCtx) {
        super(xCtx);

        try {
            cpuFamily = Utils.loadJSONFromAsset(xCtx, "cpu_family.json");
            cpuManufacturer = Utils.loadJSONFromAsset(xCtx, "cpu_manufacturer.json");

            Category c = new Category("CPUS", "cpus");
            c.put("ARCH", new CategoryValue(getArch(), "ARCH", "arch"));
            c.put("CORE", new CategoryValue(getCPUCore(), "CORE", "core"));
            c.put("FAMILYNAME", new CategoryValue(getFamilyName(), "FAMILYNAME", "familyname"));
            c.put("FAMILYNUMBER", new CategoryValue(getFamilyNumber(), "FAMILYNUMBER", "familynumber"));
            c.put("MANUFACTURER", new CategoryValue(getManufacturer(), "MANUFACTURER", "manufacturer"));
            c.put("MODEL", new CategoryValue(getModel(), "MODEL", "model"));
            c.put("NAME", new CategoryValue(getCpuName(), "NAME", "name"));
            c.put("SPEED", new CategoryValue(getCpuFrequency(), "SPEED", "cpuFrequency"));

            this.add(c);

        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

    }

    /**
     * Get the CPU Architecture
     *
     * @return String with the Cpu Architecture
     */
    public String getArch() {
        return System.getProperty("os.arch");
    }

    /**
     * Get the CPU Number Core
     *
     * @return String with the Cpu Core
     */
    public String getCPUCore() {
        if (Build.VERSION.SDK_INT >= 17) {
            return String.valueOf(Runtime.getRuntime().availableProcessors());
        } else {
            return String.valueOf(getNumCoresOldPhones());
        }
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     *
     * @return The number of cores, or 1 if failed to get result
     */
    private int getNumCoresOldPhones() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]+", pathname.getName());
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

    /**
     * Get the CPU Family Name
     *
     * @return String with the Cpu Family Name
     */
    public String getFamilyName() {
        String value = "N/A";
        try {
            Map<String, String> cpuInfo = Utils.getCatMapInfo("/proc/cpuinfo");
            String shortFamily = Utils.getValueMapInfo(cpuInfo, "CPU part");
            if (!"".equals(shortFamily)) {
                JSONArray jr = new JSONArray(cpuFamily);
                for (int i = 0; i < jr.length(); i++) {
                    JSONObject c = jr.getJSONObject(i);
                    String id = c.getString("id");
                    if (id.startsWith(shortFamily)) {
                        value = c.getString("name");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            FILog.e(e.getMessage());
        }
        return value;
    }

    /**
     * Get the CPU Family Name
     *
     * @return String with the Cpu Family Name
     */
    public String getFamilyNumber() {
        String value = "N/A";
        try {
            Map<String, String> cpuInfo = Utils.getCatMapInfo("/proc/cpuinfo");
            String cpuFamily = Utils.getValueMapInfo(cpuInfo, "cpu family").trim();
            if (!"".equals(cpuFamily)) {
                value = cpuFamily;
            }
        } catch (Exception e) {
            FILog.e(e.getMessage());
        }
        return value;
    }

    /**
     * Get the CPU Manufacturer
     *
     * @return String with the Cpu Manufacturer
     */
    public String getManufacturer() {
        String value = "N/A";
        try {
            String cpuPart = Utils.getSystemProperty("ro.board.platform").trim();
            JSONArray jr = new JSONArray(cpuManufacturer);
            for (int i = 0; i < jr.length(); i++) {
                JSONObject c = jr.getJSONObject(i);
                JSONArray ids = c.getJSONArray("id");
                for (int j = 0; j < ids.length(); j++) {
                    String idObject = ids.getString(j);
                    if (cpuPart.toLowerCase().startsWith(idObject)) {
                        value = c.getString("name");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            FILog.e(e.getMessage());
        }
        return value;
    }

    /**
     * Get the CPU Model
     *
     * @return String with the Cpu Model
     */
    public String getModel() {
        String value = "N/A";
        try {
            Map<String, String> cpuInfo = Utils.getCatMapInfo("/proc/cpuinfo");
            String hardware = Utils.getValueMapInfo(cpuInfo, "Hardware").trim();
            if (!"".equals(hardware)){
                value = hardware;
            }
        } catch (FileNotFoundException e) {
            FILog.e(e.getMessage());
        }
        return value;
    }

    /**
     * Get the CPU Name
     * @return String with the name
     * @throws IOException return exception
     */

    public String getCpuName() throws IOException {
        String cpuname = "";
        FileReader fr = null;
        BufferedReader br = null;
        try {
            File f = new File(CPUINFO);
            fr = new FileReader(f);
            br = new BufferedReader(fr, 8 * 1024);
            String infos = br.readLine();
            cpuname = infos.replaceAll("(.*):\\ (.*)", "$2");
        } catch (IOException e) {
            FILog.e(e.getMessage());
        } finally {
            if(fr != null) {
                fr.close();
            }
            if(br != null) {
                br.close();
            }
        }
        return cpuname;
    }

    /**
     * Get the CPU Frequency
     * @return String with the Cpu Frequency
     * @throws IOException return exception
     */
    public String getCpuFrequency() throws IOException {
        String cpuFrequency = "N/A";
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile(CPUINFO_MAX_FREQ, "r");
            cpuFrequency = String.valueOf(Integer.valueOf(reader.readLine()) / 1000);
            reader.close();
        } catch (IOException e) {
            FILog.e(e.getMessage());
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return cpuFrequency;
    }
}
