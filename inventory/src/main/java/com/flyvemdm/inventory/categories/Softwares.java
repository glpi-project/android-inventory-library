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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;

import com.flyvemdm.inventory.FILog;

import java.util.List;

/**
 * This class get all the information of the Softwate
 */
public class Softwares extends Categories {

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
    private PackageManager package_manager;
    private String mFrom = "Android";

    public Softwares(Context xCtx) {
        super(xCtx);
        package_manager = mCtx.getPackageManager();
        List<ApplicationInfo> packages = package_manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {

            Category c = new Category(mCtx, "SOFTWARES");

            c.put("NAME", getName(p));
            c.put("VERSION", getVersion(p));
            c.put("FILESIZE", getFilesize(p));
            c.put("FROM", mFrom);

            this.add(c);
        }
    }

    public String getName(ApplicationInfo p) {
        String mName = "";

        if (p.name != null) {
            mName = p.name;
        } else if (p.className != null) {
            mName = p.className;
        } else if (p.packageName != null) {
            mName = p.packageName;
        }

        return mName;
    }

    public String getVersion(ApplicationInfo p) {
        String mVersion = "";
        try {
            PackageInfo pi = package_manager.getPackageInfo(p.packageName, PackageManager.GET_META_DATA);
            mVersion = pi.versionName;
        } catch (NameNotFoundException e) {
            FILog.e(e.getMessage());
        }

        return mVersion;
    }

    public String getFilesize(ApplicationInfo p) {
        PackageStats stats = new PackageStats(p.packageName);
        return String.valueOf(stats.cacheSize + stats.codeSize + stats.dataSize);

    }
}
