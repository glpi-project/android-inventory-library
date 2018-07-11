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
import android.os.Build;

import org.flyve.inventory.FILog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * This class get all the information of the Environment
 */
public class OperatingSystem extends Categories {

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
    private static final long serialVersionUID = 3528873342443549732L;

    private Properties props;
    private Context xCtx;

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param obj the reference object with which to compare
     * @return boolean true if the object is the same as the one given in argument
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (!super.equals(obj));
    }

    /**
     * Returns a hash code value for the object
     *
     * @return int a hash code value for the object
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.xCtx != null ? this.xCtx.hashCode() : 0);
        hash = 89 * hash + (this.props != null ? this.props.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the context and the Hardware information
     *
     * @param xCtx Context where this class work
     */
    public OperatingSystem(Context xCtx) {
        super(xCtx);

        this.xCtx = xCtx;

        try {
            props = System.getProperties();

            Category c = new Category("OPERATINGSYSTEM", "operatingSystem");
            String architecture = "";

            ArrayList<HashMap<String, String>> arr = getDeviceProperties();
            for (int i = 0; i < arr.size(); i++) {
                HashMap<String, String> map = arr.get(i);

                if (map.get("ro.product.cpu.abilist")!=null) {
                    if(architecture.trim().isEmpty()) {
                        architecture = map.get("ro.product.cpu.abilist");
                    }
                }

                if (map.get("ro.product.cpu.abilist64")!=null) {
                    if(architecture.trim().isEmpty()) {
                        architecture = map.get("ro.product.cpu.abilist64");
                    }
                }
            }

            c.put("ARCH", new CategoryValue(architecture.trim(), "ARCH", "architecture"));
            // review SystemClock.elapsedRealtime()
            c.put("BOOT_TIME", new CategoryValue(" ", "BOOT_TIME", "bootTime"));
            c.put("DNS_DOMAIN", new CategoryValue(" ", "DNS_DOMAIN", "dnsDomain"));
            c.put("FQDN", new CategoryValue(" ", "FQDN", "FQDN"));
            c.put("FULL_NAME", new CategoryValue(getAndroidVersion(Build.VERSION.SDK_INT) + " api " + Build.VERSION.SDK_INT , "FULL_NAME", "fullName"));
            c.put("HOSTID", new CategoryValue(" ", "HOSTID", "hostId"));
            c.put("KERNEL_NAME", new CategoryValue("linux", "KERNEL_NAME", "kernelName"));
            c.put("KERNEL_VERSION", new CategoryValue(getKernelVersion(), "KERNEL_VERSION", "kernelVersion"));
            c.put("NAME", new CategoryValue(getAndroidVersion(Build.VERSION.SDK_INT), "NAME", "Name"));
            c.put("SSH_KEY", new CategoryValue(" ", "SSH_KEY", "sshKey"));
            c.put("VERSION", new CategoryValue(String.valueOf(Build.VERSION.SDK_INT), "VERSION", "Version"));

            this.add(c);

        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }


    private ArrayList<HashMap<String, String>> getDeviceProperties() {
        try {
            // Run the command
            Process process = Runtime.getRuntime().exec("getprop");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            // Grab the results
            ArrayList<HashMap<String, String>> arr = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] value = line.split(":");
                HashMap<String, String> map = new HashMap<>();
                map.put(removeBraket(value[0]), removeBraket(value[1]));
                arr.add(map);
            }

            return arr;
        } catch (IOException ex) {
            FILog.e(ex.getMessage());
        }

        return new ArrayList<>();
    }

    private String removeBraket(String str) {
        return str.replaceAll("\\[", "").replaceAll("]", "");
    }

    public static String getKernelVersion() {
        try {
            Process p = Runtime.getRuntime().exec("uname -a");
            InputStream is;
            if (p.waitFor() == 0) {
                is = p.getInputStream();
            } else {
                return "";
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is),
                    64);
            String line = br.readLine();
            br.close();
            return line;
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
            return "N/A";
        }
    }

    private String getAndroidVersion(int sdk) {
        switch (sdk) {
            case 1:
                return "Android 1.0";
            case 2:
                return "Petit Four (Android 1.1)";
            case 3:
                return "Cupcake (Android 1.5)";
            case 4:
                return "Donut (Android 1.6)";
            case 5:
                return "Eclair (Android 2.0)";
            case 6:
                return "Eclair (Android 2.0.1)";
            case 7:
                return "Eclair (Android 2.1)";
            case 8:
                return "Froyo (Android 2.2)";
            case 9:
                return "Gingerbread (Android 2.3)";
            case 10:
                return "Gingerbread (Android 2.3.3)";
            case 11:
                return "Honeycomb (Android 3.0)";
            case 12:
                return "Honeycomb (Android 3.1)";
            case 13:
                return "Honeycomb (Android 3.2)";
            case 14:
                return "Ice Cream Sandwich (Android 4.0)";
            case 15:
                return "Ice Cream Sandwich (Android 4.0.3)";
            case 16:
                return "Jelly Bean (Android 4.1)";
            case 17:
                return "Jelly Bean (Android 4.2)";
            case 18:
                return "Jelly Bean (Android 4.3)";
            case 19:
                return "KitKat (Android 4.4)";
            case 20:
                return "KitKat Watch (Android 4.4)";
            case 21:
                return "Lollipop (Android 5.0)";
            case 22:
                return "Lollipop (Android 5.1)";
            case 23:
                return "Marshmallow (Android 6.0)";
            case 24:
                return "Nougat (Android 7.0)";
            case 25:
                return "Nougat (Android 7.1.1)";
            case 26:
                return "Oreo (Android 8.0)";
            case 27:
                return "Oreo (Android 8.1)";
            default:
                return "";
        }
    }
}