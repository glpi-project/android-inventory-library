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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;
import org.flyve.inventory.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * This class get all the information of the Bios
 */
public class Bios extends Categories {

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
	private static final long serialVersionUID = -559572118090134691L;
	private static final String CPUINFO = "/proc/cpuinfo";
	private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
	private final Context context;

	// <!ELEMENT BIOS (SMODEL, SMANUFACTURER, SSN, BDATE, BVERSION,
	//	BMANUFACTURER, MMANUFACTURER, MSN, MMODEL, ASSETTAG, ENCLOSURESERIAL,
	//	BIOSSERIAL, TYPE, SKUNUMBER)>

	/**
	 * This constructor trigger get all the information about Bios
	 * @param xCtx Context where this class work
	 */
	public Bios(Context xCtx) {
		super(xCtx);

		context = xCtx;

		try {

			Category c = new Category("BIOS", "bios");

			c.put("ASSETTAG", new CategoryValue(getAssetTag(), "ASSETTAG", "assettag"));
			c.put("BDATE", new CategoryValue(getBiosDate(), "BDATE", "biosReleaseDate"));
			c.put("BMANUFACTURER", new CategoryValue(getBiosManufacturer(), "BMANUFACTURER", "biosManufacturer"));
			c.put("BVERSION", new CategoryValue(getBiosVersion(), "BVERSION", "biosVersion"));
			c.put("MMANUFACTURER", new CategoryValue(getManufacturer(), "MMANUFACTURER", "motherBoardManufacturer"));
			c.put("MMODEL", new CategoryValue(getModel(), "MMODEL", "motherBoardModel"));
			c.put("MSN", new CategoryValue(getMotherBoardSerial(), "MSN", "motherBoardSerialNumber"));
			c.put("SMANUFACTURER", new CategoryValue(getManufacturer(), "SMANUFACTURER", "systemManufacturer"));
			c.put("SMODEL", new CategoryValue(getModel(), "SMODEL", "systemModel"));
			c.put("SSN", new CategoryValue(getSystemSerialNumber(), "SSN", "systemSerialNumber"));

			this.add(c);
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS, ex.getMessage()));
		}
	}

	/**
	 * Get the Bios Date
	 * @return string with the date in simple format
	 */
	public String getBiosDate() {
		String dateInfo = "N/A";
		try {
			dateInfo = Utils.getCatInfo("/sys/devices/virtual/dmi/id/bios_date");
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_DATE, ex.getMessage()));
		}
		return !dateInfo.equals("") ? dateInfo : "N/A";
	}

	/**
	 * Get the Bios Manufacturer
	 * @return string with the manufacturer
	 */
	public String getBiosManufacturer() {
		String manufacturer = "N/A";
		try {
			manufacturer= Build.MANUFACTURER;
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_MANUFACTURER, ex.getMessage()));
		}
		return manufacturer;
	}

	/**
	 * Get the Bios Version
	 * @return string with the bootloader version
	 */
	public String getBiosVersion() {
		String dateInfo = "N/A";
		try {
			dateInfo = Utils.getCatInfo("/sys/devices/virtual/dmi/id/bios_version");
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_VERSION, ex.getMessage()));
		}
		return !dateInfo.equals("") ? dateInfo : "N/A";
	}

	/**
	 * Get the Mother Board Manufacturer
	 * @return string with the manufacturer
	 */
	public String getManufacturer() {
		String manufacturer = "N/A";
		try {
			manufacturer = Build.MANUFACTURER;
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_BOARD_MANUFACTURER, ex.getMessage()));
		}
		return manufacturer;
	}

	/**
	 * Get the Mother Board Model
	 * @return string with the model
	 */
	public String getModel() {
		String model = "N/A";
		try {
			model = Build.MODEL;
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_MOTHER_BOARD_MODEL, ex.getMessage()));
		}
		return model;
	}

	/**
	 * Get the Build Tag
	 * @return string with the model
	 */
	public String getAssetTag() {
		String tags = "N/A";
		try {
			tags = Build.TAGS;
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_TAG, ex.getMessage()));
		}
		return tags;
	}

	/**
	 * Get the serial mother board
	 * @return string with the serial mother board
	 */
	public String getMotherBoardSerial() {
		String dateInfo = "N/A";
		try {
			dateInfo = Utils.getCatInfo("/sys/devices/virtual/dmi/id/board_serial");
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_MOTHER_BOARD_SERIAL, ex.getMessage()));
		}
		return !dateInfo.equals("") ? dateInfo : "N/A";
	}


	/**
	 * Get the System Unique Identifier
	 * @return string with Unique Identifier
	 */
	public String getSystemUniqueID() {
		String uniqueID;

		SharedPreferences sharedPrefs = context.getSharedPreferences(
				PREF_UNIQUE_ID, Context.MODE_PRIVATE);
		uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
		if (uniqueID == null) {
			uniqueID = UUID.randomUUID().toString().replace("-", "");
			SharedPreferences.Editor editor = sharedPrefs.edit();
			editor.putString(PREF_UNIQUE_ID, uniqueID);
			editor.apply();
		}

		return uniqueID;

	}


	/**
	 * Get the System serial number
	 * @return string with the serial number
	 */
	@SuppressLint("HardwareIds")
	public String getSystemSerialNumber() {
		String systemSerialNumber = "Unknown";
		try {
			//Try to get the serial by reading /proc/cpuinfo
			String serial = "";

			//First, use the hidden API!
			//Versions < Android 7 get bad serial with Build.SERIAL
			serial = getSerialFromPrivateAPI();


			//Second, try to get serial number from regular API!
			if (serial.equals("")) {
				if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
						ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
							== PackageManager.PERMISSION_GRANTED) {
					serial = android.os.Build.getSerial();
				} else{
					// Mother Board Serial Number
					// Since in 2.3.3 a.k.a gingerbread
					serial = Build.SERIAL;
				}

				//Third try to get serial number from CpuInfo
				if (serial.equals("") || serial.equalsIgnoreCase(android.os.Build.UNKNOWN)) {

					serial = this.getSerialNumberFromCpuInfo();
					if (!serial.equals("") && !serial.equals("0000000000000000")) {
						systemSerialNumber = serial;
					}

				}else{
					systemSerialNumber = serial;
				}

			} else {
				systemSerialNumber = serial;
			}

		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_SYSTEM_SERIAL, ex.getMessage()));
		}

		//serial cannot by empty
		//get uuid of need
		if(systemSerialNumber.equalsIgnoreCase("Unknown")
			|| systemSerialNumber.equalsIgnoreCase("N/A")
			|| systemSerialNumber.isEmpty()
			|| systemSerialNumber.equalsIgnoreCase(android.os.Build.UNKNOWN)){
			Hardware hardware = new Hardware(context);
			systemSerialNumber = hardware.getUUID();
		}
		return systemSerialNumber;
	}

	/**
	 * This is a call to a private api to get Serial number
	 * @return String with a Serial Device
	 */
	private String getSerialFromPrivateAPI() {

		String serialNumber;

		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class);
			get.setAccessible(true);

			// (?) Lenovo Tab (https://stackoverflow.com/a/34819027/1276306)
			serialNumber = (String) get.invoke(c, "gsm.sn1");

			if (serialNumber.equals(""))
				// Samsung Galaxy S5 (SM-G900F) : 6.0.1
				// Samsung Galaxy S6 (SM-G920F) : 7.0
				// Samsung Galaxy Tab 4 (SM-T530) : 5.0.2
				// (?) Samsung Galaxy Tab 2 (https://gist.github.com/jgold6/f46b1c049a1ee94fdb52)
				serialNumber = (String) get.invoke(c, "ril.serialnumber");

			if (serialNumber.equals(""))
				// Archos 133 Oxygen : 6.0.1
				// Google Nexus 5 : 6.0.1
				// Hannspree HANNSPAD 13.3" TITAN 2 (HSG1351) : 5.1.1
				// Honor 5C (NEM-L51) : 7.0
				// Honor 5X (KIW-L21) : 6.0.1
				// Huawei M2 (M2-801w) : 5.1.1
				// (?) HTC Nexus One : 2.3.4 (https://gist.github.com/tetsu-koba/992373)
				serialNumber = (String) get.invoke(c, "ro.serialno");

			if (serialNumber.equals(""))
				// (?) Samsung Galaxy Tab 3 (https://stackoverflow.com/a/27274950/1276306)
				serialNumber = (String) get.invoke(c, "sys.serialnumber");

		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_SERIAL_PRIVATE, ex.getMessage()));
			serialNumber = "";
		}

		return serialNumber;
	}

	/**
	 * Get the serial by reading /proc/cpuinfo
	 * @return String
	 */
	private String getSerialNumberFromCpuInfo() {
		String serial = "";
		try {
			File f = new File(CPUINFO);
			FileReader fr = null;
			try {
				fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr, 8 * 1024);
				String line;
				while ((line = br.readLine()) != null) {
					if (line.startsWith("Serial")) {
						InventoryLog.d(line);
						String[] results = line.split(":");
						serial = results[1].trim();
					}
				}
				br.close();
				fr.close();
			} catch (IOException e) {
				InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_CPU_SERIAL, e.getMessage()));
			} finally {
				if (fr != null) {
					fr.close();
				}
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.BIOS_CPU_SERIAL, ex.getMessage()));
		}

		return serial.trim();
	}
}
