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
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.Cpus;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CpusTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getArch() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getArch());
    }

    @Test
    public void getCPUCore() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getCPUCore());
    }

    @Test
    public void getFamilyName() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getFamilyName());
    }

    @Test
    public void getFamilyNumber() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getFamilyNumber());
    }

    @Test
    public void getManufacturer() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getManufacturer());
    }

    @Test
    public void getModel() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getModel());
    }

    @Test
    public void getCpuName() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getCpuName());
    }

    @Test
    public void getCpuThread() throws Exception {
        Cpus cpus = new Cpus(appContext);
        assertNotEquals("", cpus.getCpuThread());
    }

    @Test
    public void getCpuFrequency() throws Exception {
        // work on real device
        if(!Utils.isEmulator()) {
            Cpus cpus = new Cpus(appContext);
            assertNotEquals("", cpus.getCpuFrequency());
        } else {
            assertTrue(true);
        }
    }
}