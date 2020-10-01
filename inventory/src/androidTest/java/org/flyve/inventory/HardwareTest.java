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
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.Hardware;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class HardwareTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getDatelastloggeduser() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getDateLastLoggedUser());

    }

    @Test
    public void getLastloggeduser() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getLastLoggedUser());
    }

    @Test
    public void getUser() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getUserId());
    }

    @Test
    public void getName() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getName());
    }

    @Test
    public void getOsversion() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getOsVersion());
    }

    @Test
    public void getArchname() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getArchName());
    }

    @Test
    public void getUUID() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getUUID());
    }

}