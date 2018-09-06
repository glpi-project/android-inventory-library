/**
 *
 * Copyright 2017 Teclib.
 * Copyright 2010-2016 by the FusionInventory Development
 *
 * http://www.fusioninventory.org/
 * https://github.com/fusioninventory/fusioninventory-android
 *
 * ------------------------------------------------------------------------
 *
 * LICENSE
 *
 * This file is part of FusionInventory project.
 *
 * FusionInventory is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FusionInventory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @update    07/06/2017
 * @license   GPLv2 https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * @link      https://github.com/fusioninventory/fusioninventory-android
 * @link      http://www.fusioninventory.org/
 * ------------------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.util.Log;

import org.flyve.inventory.FILog;
import org.flyve.inventory.usbManager.SysBusUsbDevice;
import org.flyve.inventory.usbManager.SysBusUsbManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class get all the information of the USB
 */
public class Usb extends Categories {

	/*
     * The serialization runtime associates with each serializable class a version number,
     * called a serialVersionUID, which is used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object that are compatible
     * with respect to serialization. If the receiver has loaded a class for the object that has a
     * different serialVersionUID than that of the corresponding sender's class, then deserialization
     * will result in an  InvalidClassException
     *
     *  from: https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     */
    private static final long serialVersionUID = 4846706700566208666L;

	/**
	 * This constructor load the context and the Usb information
	 * @param xCtx Context where this class work
	 */
    public Usb(Context xCtx) {
        super(xCtx);
        
        //USB inventory comes with SDK level 12 !
        try {
			SysBusUsbDevice usb = getSysBusUsbDevice();

			Category c = new Category("USBDEVICES", "usbDevices");
			c.put("CLASS", new CategoryValue(usb.getServiceClass(), "CLASS", "class"));
			c.put("PRODUCTID", new CategoryValue(usb.getPid(), "PRODUCTID", "productId"));
			c.put("VENDORID", new CategoryValue(usb.getVid(), "VENDORID", "vendorId"));
			c.put("SUBCLASS", new CategoryValue(usb.getDeviceSubClass(), "SUBCLASS", "subClass"));
			c.put("MANUFACTURER", new CategoryValue(usb.getReportedProductName(), "MANUFACTURER", "manufacturer"));
			c.put("CAPTION", new CategoryValue(usb.getUsbVersion(), "CAPTION", "caption"));
			c.put("SERIAL", new CategoryValue(usb.getSerialNumber(), "SERIAL", "serial"));

			this.add(c);
		} catch (Exception ex) {
			FILog.e(ex.getMessage());
		}

    }

	public SysBusUsbDevice getSysBusUsbDevice() {
		SysBusUsbManager usbManager = new SysBusUsbManager("/sys/bus/usb/devices/");
		Map<String, SysBusUsbDevice> devices = usbManager.getUsbDevices();
		return devices.get("usb1");
	}
}
