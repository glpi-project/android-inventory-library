package com.flyvemdm.inventory;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.flyvemdm.inventory.categories.Usb;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

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
 * @author    Rafael Hernandez
 * @date      16/6/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

@RunWith(AndroidJUnit4.class)
public class UsbTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getClassTest() throws Exception {
        if(Build.VERSION.SDK_INT > 12) {
            Usb usb = new Usb(appContext);
            UsbManager manager = (UsbManager) appContext.getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> devices = manager.getDeviceList();
            for (String key : devices.keySet()) {
                UsbDevice mydevice = devices.get(key);
                assertNotEquals("", usb.getClass(mydevice));
            }
        }
    }

    @Test
    public void getProductid() throws Exception {
        if(Build.VERSION.SDK_INT > 12) {
            Usb usb = new Usb(appContext);
            UsbManager manager = (UsbManager) appContext.getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> devices = manager.getDeviceList();
            for (String key : devices.keySet()) {
                UsbDevice mydevice = devices.get(key);
                assertNotEquals("", usb.getProductid(mydevice));
            }
        }
    }

    @Test
    public void getVendorid() throws Exception {
        if(Build.VERSION.SDK_INT > 12) {
            Usb usb = new Usb(appContext);
            UsbManager manager = (UsbManager) appContext.getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> devices = manager.getDeviceList();
            for (String key : devices.keySet()) {
                UsbDevice mydevice = devices.get(key);
                assertNotEquals("", usb.getVendorid(mydevice));
            }
        }
    }

    @Test
    public void getSubclass() throws Exception {
        if(Build.VERSION.SDK_INT > 12) {
            Usb usb = new Usb(appContext);
            UsbManager manager = (UsbManager) appContext.getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> devices = manager.getDeviceList();
            for (String key : devices.keySet()) {
                UsbDevice mydevice = devices.get(key);
                assertNotEquals("", usb.getSubclass(mydevice));
            }
        }
    }

}