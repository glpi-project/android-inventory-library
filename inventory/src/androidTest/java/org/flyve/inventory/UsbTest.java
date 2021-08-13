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

import org.flyve.inventory.categories.Usb;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class UsbTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getClassTest() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getServiceClass());
    }

    @Test
    public void getProductid() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getPid());
    }

    @Test
    public void getVendorid() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getVid());
    }

    @Test
    public void getSubclass() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getDeviceSubClass());
    }

    @Test
    public void getManufacturer() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getReportedProductName());
    }

    @Test
    public void getCaption() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getUsbVersion());
    }

    @Test
    public void getSerialNumber() {
        Usb usb = new Usb(appContext);
        assertNotEquals("", usb.getSerialNumber());
    }

}