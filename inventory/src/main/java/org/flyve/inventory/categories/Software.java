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
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;

import org.flyve.inventory.FILog;

import java.util.List;

/**
 * This class get all the information of the Software
 */
public class Software extends Categories {

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
    private static final String FROM = "Android";
    private PackageManager packageManager;

    /**
     * Indicates whether some other object is "equal to" this one
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
     * @return int a hash code value for the object
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.packageManager != null ? this.packageManager.hashCode() : 0);
        return hash;
    }

    /**
    * This constructor get the information about Software
    * @param xCtx Context where this class work
    */
    public Software(Context xCtx) {
        super(xCtx);
        try {
            packageManager = xCtx.getPackageManager();
            List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo p : packages) {

                Category c = new Category("SOFTWARES", "softwares");

                String fileSize = getFilesize(p);

                c.put("NAME", new CategoryValue(getName(p), "NAME", "name", false, true));
                c.put("COMMENTS", new CategoryValue(getPackage(p), "COMMENTS", "comments"));
                c.put("VERSION", new CategoryValue(getVersion(p), "VERSION", "version"));
                c.put("FILESIZE", new CategoryValue(fileSize, "FILESIZE", "fileSize"));
                c.put("FROM", new CategoryValue(FROM, "FROM", "from"));
                c.put("INSTALLDATE", new CategoryValue(getInstallDate(p), "INSTALLDATE", "installDate"));

                this.add(c);

            }
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }

    /**
     * Get the name of the application
     * @param p ApplicationInfo
     * @return string the application name
     */
    public String getName(ApplicationInfo p) {
        return packageManager.getApplicationLabel(p).toString();
    }

    public String getPackage(ApplicationInfo p) {
        return p.packageName;
    }

    /**
     * Get the version of the application
     * @param p ApplicationInfo
     * @return string the application version
     */
    public String getInstallDate(ApplicationInfo p) {
        String mInstalled = "";

        try {
            PackageInfo pi = packageManager.getPackageInfo(p.packageName, PackageManager.GET_META_DATA);
            mInstalled = String.valueOf(pi.firstInstallTime);
        } catch (NameNotFoundException e) {
            FILog.e(e.getMessage());
        }

        return mInstalled;
    }

    /**
     * Get the version of the application
     * @param p ApplicationInfo
     * @return string the application version
     */
    public String getVersion(ApplicationInfo p) {
        String mVersion = "";
        try {
            PackageInfo pi = packageManager.getPackageInfo(p.packageName, PackageManager.GET_META_DATA);
            mVersion = pi.versionName;
        } catch (NameNotFoundException e) {
            FILog.e(e.getMessage());
        }

        return mVersion;
    }

    /**
     * Get the size of the application
     * @param p ApplicationInfo
     * @return string the sum of the cache, code and data size of the application
     */
    public String getFilesize(ApplicationInfo p) {
        PackageStats stats = new PackageStats(p.packageName);
        return String.valueOf(stats.cacheSize + stats.codeSize + stats.dataSize);

    }
}
