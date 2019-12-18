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
import org.flyve.inventory.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

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
    private final Context context;

    /**
     * This constructor trigger get all the information about Cpus
     * @param xCtx Context where this class work
     */
    public Cpus(Context xCtx) {
        super(xCtx);

        context = xCtx;

        cpuFamily = Utils.loadJSONFromAsset(context, "cpu_family.json");
        cpuManufacturer = Utils.loadJSONFromAsset(context, "cpu_manufacturer.json");

        try {

            Category c = new Category("CPUS", "cpus");
            c.put("ARCH", new CategoryValue(getArch(), "ARCH", "arch"));
            c.put("CORE", new CategoryValue(getCPUCore(), "CORE", "core"));
            c.put("FAMILYNAME", new CategoryValue(getFamilyName(), "FAMILYNAME", "familyname"));
            c.put("FAMILYNUMBER", new CategoryValue(getFamilyNumber(), "FAMILYNUMBER", "familynumber"));
            c.put("MANUFACTURER", new CategoryValue(getManufacturer(), "MANUFACTURER", "manufacturer"));
            c.put("MODEL", new CategoryValue(getModel(), "MODEL", "model"));
            c.put("NAME", new CategoryValue(getCpuName(), "NAME", "name"));
            c.put("SPEED", new CategoryValue(getCpuFrequency(), "SPEED", "cpuFrequency"));
            c.put("THREAD", new CategoryValue(getCpuThread(), "THREAD", "thread"));

            this.add(c);
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU, ex.getMessage()));
        }
    }

    /**
     * Get the CPU Number Core
     *
     * @return String with the Cpu Core
     */
    public String getCPUCore() {
        int value = 0;
        try {
            String a = Utils.getCatInfo("/sys/devices/system/cpu/present");
            if (!a.equals("")) {
                if (a.contains("-")) {
                    value = Integer.parseInt(a.split("-")[1]);
                }
                return String.valueOf(++value);
            } else {
                return String.valueOf(Runtime.getRuntime().availableProcessors());
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_CORE, ex.getMessage()));
        }
        return String.valueOf(value);
    }

    /**
     * Get the CPU Architecture
     *
     * @return String with the Cpu Architecture
     */
    public String getArch() {
        String value = "N/A";
        try {
            value = System.getProperty("os.arch");
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_ARCH, ex.getMessage()));
        }
        return value;
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
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_FAMILY_NAME, ex.getMessage()));
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
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_FAMILY_NUMBER, ex.getMessage()));
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
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_MANUFACTURER, ex.getMessage()));
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
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_MODEL, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the CPU Name
     * @return String with the name
     */

    public String getCpuName() {
        String value = "N/A";
        try {
            Map<String, String> cpuInfo = Utils.getCatMapInfo("/proc/cpuinfo");
            String cpuName = Utils.getValueMapCase(cpuInfo, "Processor").trim();
            if (!"".equals(cpuName)){
                value = cpuName;
            } else {
                cpuName = Utils.getValueMapCase(cpuInfo, "model name").trim();
                if (!"".equals(cpuName)){
                    value = cpuName;
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_NAME, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get the CPU Frequency
     * @return String with the Cpu Frequency
     */
    public String getCpuFrequency() {
        String cpuFrequency = "N/A";
        try {
            RandomAccessFile reader = null;
            try {
                reader = new RandomAccessFile(CPUINFO_MAX_FREQ, "r");
                cpuFrequency = String.valueOf(Integer.valueOf(reader.readLine()) / 1000);
                reader.close();
            } catch (IOException e) {
                InventoryLog.e(e.getMessage());
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_FREQUENCY, ex.getMessage()));
        }
        return cpuFrequency;
    }

    /**
     * Gets the number of threads available in this device.é
     * @return The number of threads
     */
    public String getCpuThread() {
        String value = "N/A";
        try {
            value = String.valueOf(Runtime.getRuntime().availableProcessors());
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CPU_THREAD, ex.getMessage()));
        }
        return value;
    }
}
