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
import android.os.Environment;
import android.support.test.InstrumentationRegistry;

import org.flyve.inventory.categories.Drives;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotEquals;

    public class DrivesTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    File froot = Environment.getRootDirectory();
    File fexternal = Environment.getExternalStorageDirectory();
    File fdata = Environment.getDataDirectory();
    File fcache = Environment.getDownloadCacheDirectory();

    @Test
    public void getVolumn() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getVolume(froot));
        assertNotEquals("", drives.getVolume(fexternal));
        assertNotEquals("", drives.getVolume(fdata));
        String secondaryStorage = System.getenv("SECONDARY_STORAGE");
        if (secondaryStorage != null) {
            assertNotEquals("", drives.getVolume(new File(secondaryStorage)));
        }
    }

    @Test
    public void getTotal() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getTotal(froot));
        assertNotEquals("", drives.getTotal(fexternal));
        assertNotEquals("", drives.getTotal(fdata));
        assertNotEquals("", drives.getTotal(fcache));
        String secondaryStorage = System.getenv("SECONDARY_STORAGE");
        if (secondaryStorage != null) {
            assertNotEquals("", drives.getTotal(new File(secondaryStorage)));
        }
    }

    @Test
    public void getFree() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getFreeSpace(froot));
        assertNotEquals("", drives.getFreeSpace(fexternal));
        assertNotEquals("", drives.getFreeSpace(fdata));
        assertNotEquals("", drives.getFreeSpace(fcache));
        String secondaryStorage = System.getenv("SECONDARY_STORAGE");
        if (secondaryStorage != null) {
            assertNotEquals("", drives.getFreeSpace(new File(secondaryStorage)));
        }
    }

    @Test
    public void getFileSystem() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getFileSystem(froot));
        assertNotEquals("", drives.getFileSystem(fexternal));
        assertNotEquals("", drives.getFileSystem(fdata));
        assertNotEquals("", drives.getFileSystem(fcache));
        String secondaryStorage = System.getenv("SECONDARY_STORAGE");
        if (secondaryStorage != null) {
            assertNotEquals("", drives.getFileSystem(new File(secondaryStorage)));
        }
    }

    @Test
    public void getType() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getType(froot));
        assertNotEquals("", drives.getType(fexternal));
        assertNotEquals("", drives.getType(fdata));
        assertNotEquals("", drives.getType(fcache));
        String secondaryStorage = System.getenv("SECONDARY_STORAGE");
        if (secondaryStorage != null) {
            assertNotEquals("", drives.getType(new File(secondaryStorage)));
        }
    }

}