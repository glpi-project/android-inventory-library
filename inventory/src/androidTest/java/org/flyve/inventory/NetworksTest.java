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

import org.flyve.inventory.categories.Networks;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class NetworksTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getMacaddr() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getMacAddress());
    }

    @Test
    public void getSpeed() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getSpeed());
    }

    @Test
    public void getBSSID() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getBSSID());
    }

    @Test
    public void getSSID() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getSSID());
    }

    @Test
    public void getIpgateway() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getIpgateway());
    }

    @Test
    public void getIpaddress() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getIpAddress());
    }

    @Test
    public void getIpmask() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getIpMask());
    }

    @Test
    public void getIpdhcp() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getIpDhCp());
    }

    @Test
    public void getIpSubnet() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getIpSubnet());
    }

    @Test
    public void getStatus() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getStatus());
    }

    @Test
    public void getDescription() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getDescription());
    }

    @Test
    public void getAddressIpV6() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getAddressIpV6());
    }

    @Test
    public void getMaskIpV6() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getMaskIpV6());
    }

    @Test
    public void getSubnetIpV6() {
        Networks networks = new Networks(appContext);
        assertNotEquals("", networks.getSubnetIpV6());
    }

}