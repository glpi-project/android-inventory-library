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

package org.flyve.inventory.categories;

import android.content.Context;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;
import org.flyve.inventory.usbManager.SysBusUsbDevice;
import org.flyve.inventory.usbManager.SysBusUsbManager;

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
	private SysBusUsbDevice usb;
	private final Context context;

	/**
	 * This constructor load the context and the Usb information
	 * @param xCtx Context where this class work
	 */
    public Usb(Context xCtx) {
        super(xCtx);

		context = xCtx;

		//USB inventory comes with SDK level 12 !
        try {
			usb = getSysBusUsbDevice();

			Category c = new Category("USBDEVICES", "usbDevices");
			c.put("CLASS", new CategoryValue(getServiceClass(), "CLASS", "class"));
			c.put("PRODUCTID", new CategoryValue(getPid(), "PRODUCTID", "productId"));
			c.put("VENDORID", new CategoryValue(getVid(), "VENDORID", "vendorId"));
			c.put("SUBCLASS", new CategoryValue(getDeviceSubClass(), "SUBCLASS", "subClass"));
			c.put("MANUFACTURER", new CategoryValue(getReportedProductName(), "MANUFACTURER", "manufacturer"));
			c.put("CAPTION", new CategoryValue(getUsbVersion(), "CAPTION", "caption"));
			c.put("SERIAL", new CategoryValue(getSerialNumber(), "SERIAL", "serial"));

			this.add(c);
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB, ex.getMessage()));
		}

    }

	private SysBusUsbDevice getSysBusUsbDevice() {
    	try {
			SysBusUsbManager usbManager = new SysBusUsbManager("/sys/bus/usb/devices/");
			Map<String, SysBusUsbDevice> devices = usbManager.getUsbDevices();
			return devices.get("usb1");
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_SYS_BUS, ex.getMessage()));
		}
		return null;
	}

	public String getServiceClass() {
		String value = "N/A";
		try {
			if (usb != null) value = usb.getServiceClass();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_SERVICE, ex.getMessage()));
		}
		return value;
	}

	public String getPid() {
		String value = "N/A";
		try {
			value = usb.getPid();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_PID, ex.getMessage()));
		}
		return value;
	}

	public String getVid() {
		String value = "N/A";
		try {
			value = usb.getVid();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_VID, ex.getMessage()));
		}
		return value;
	}

	public String getDeviceSubClass() {
		String value = "N/A";
		try {
			value = usb.getDeviceSubClass();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_DEVICE_SUB_CLASS, ex.getMessage()));
		}
		return value;
	}

	public String getReportedProductName() {
		String value = "N/A";
		try {
			value = usb.getReportedProductName();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_REPORTED_PRODUCT_NAME, ex.getMessage()));
		}
		return value;
	}

	public String getUsbVersion() {
		String value = "N/A";
		try {
			value = usb.getUsbVersion();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_USB_VERSION, ex.getMessage()));
		}
		return value;
	}

	public String getSerialNumber() {
		String value = "N/A";
		try {
			value = usb.getSerialNumber();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.USB_SERIAL_NUMBER, ex.getMessage()));
		}
		return value;
	}
}
