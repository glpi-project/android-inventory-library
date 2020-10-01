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

package org.flyve.inventory;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.Software;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class SoftwareTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getName() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getName(p));
        }
    }

    @Test
    public void getVersion() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getVersion(p));
        }
    }

    @Test
    public void getFileSize() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getFileSize(p));
        }
    }

    @Test
    public void getFolder() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getFolder(p));
        }
    }

    @Test
    public void getRemovable() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getRemovable(p));
        }
    }

    @Test
    public void getUserID() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getUserID(p));
        }
    }

    @Test
    public void getPackage() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getPackage(p));
        }
    }

    @Test
    public void getInstallDate() {
        Software software = new Software(appContext);

        PackageManager packageManager = appContext.getPackageManager();
        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo p : packages) {
            assertNotEquals("", software.getInstallDate(p));
        }
    }

}