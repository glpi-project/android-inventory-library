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
 *  @copyright Copyright FusionInventory.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.Bios;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class BiosTest {
    private Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getBios_date() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getBiosDate());
    }

    @Test
    public void getBios_manufacturer() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getBiosManufacturer());
    }

    @Test
    public void getBios_version() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getBiosVersion());
    }

    @Test
    public void getMother_board_manufacturer() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getMotherBoardManufacturer() );
    }

    @Test
    public void getMother_board_model() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getMotherBoardModel() );
    }

    @Test
    public void getSystemSerialNumber() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getSystemSerialNumber() );
    }

    @Test
    public void getMotherBoardSerial() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getMotherBoardSerial() );
    }

    @Test
    public void getTag() throws Exception {
        Bios bios = new Bios(appContext);
        assertNotEquals("", bios.getBuildTag() );
    }

}