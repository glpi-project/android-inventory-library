package com.flyvemdm.inventory;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;

import com.flyvemdm.inventory.categories.Drives;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotEquals;

/*
 *   Copyright © 2017 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android
 *
 * flyve-mdm-android is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    rafaelhernandez
 * @date      15/6/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */public class DrivesTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    File froot = Environment.getRootDirectory();
    File fexternal = Environment.getExternalStorageDirectory();
    File fdata = Environment.getDataDirectory();
    File fcache = Environment.getDownloadCacheDirectory();

    @Test
    public void getVolumn() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getVolumn(froot));
        assertNotEquals("", drives.getVolumn(fexternal));
        assertNotEquals("", drives.getVolumn(fdata));
        assertNotEquals("", drives.getVolumn(fcache));
    }

    @Test
    public void getTotal() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getTotal(froot));
        assertNotEquals("", drives.getTotal(fexternal));
        assertNotEquals("", drives.getTotal(fdata));
        assertNotEquals("", drives.getTotal(fcache));
    }

    @Test
    public void getFree() throws Exception {
        Drives drives = new Drives(appContext);
        assertNotEquals("", drives.getFree(froot));
        assertNotEquals("", drives.getFree(fexternal));
        assertNotEquals("", drives.getFree(fdata));
        assertNotEquals("", drives.getFree(fcache));
    }

}