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
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.flyve.inventory.categories.Bluetooth;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4ClassRunner.class)  
public class BluetoothTest {

    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

    @Test
    public void getHardware_address() throws Exception {
        if(!Utils.isEmulator()) {
            Bluetooth bluetooth = new Bluetooth(appContext);
            assertNotEquals("", bluetooth.getHardwareAddress());
        } else {
            assertTrue(true);
        }
    }

    @Test
    public void getName() throws Exception {
        if(!Utils.isEmulator()) {
            Bluetooth bluetooth = new Bluetooth(appContext);
            assertNotEquals("", bluetooth.getName());
        } else {
            assertTrue(true);
        }


    }

}