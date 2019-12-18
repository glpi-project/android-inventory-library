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
import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

import java.util.List;

/**
 * This class get all the information of the Sensor Devices
 */
public class Sensors extends Categories {

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
	private final Context context;

	/**
	 * This constructor load the context and the Sensors information
	 * @param xCtx Context where this class work
	 */
	public Sensors(Context xCtx) {
		super(xCtx);

		context = xCtx;

		try {
			SensorManager sensorManager = (SensorManager) xCtx.getSystemService(Context.SENSOR_SERVICE);
			List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

			for (Sensor s : sensors) {
				Category c = new Category("SENSORS", "sensors");

				c.put("NAME", new CategoryValue(getName(s), "NAME", "name"));
				c.put("MANUFACTURER", new CategoryValue(getManufacturer(s), "MANUFACTURER", "manufacturer"));
				c.put("TYPE", new CategoryValue(getType(s), "TYPE", "type"));
				c.put("POWER", new CategoryValue(getPower(s), "POWER", "power"));
				c.put("VERSION", new CategoryValue(getVersion(s), "VERSION", "version"));

				this.add(c);
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.SENSORS, ex.getMessage()));
		}
	}

	/**
	 * Get the name of the sensor
	 * @param s Sensor
	 * @return string the sensor name
	 */
	public String getName(Sensor s) {
		String value = "N/A";
		try {
			value = s.getName();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.SENSORS_NAME, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the Manufacturer of the sensor
	 * @param s Sensor
	 * @return string the vendor of the sensor
	 */
	public String getManufacturer(Sensor s) {
		String value = "N/A";
		try {
			value = s.getVendor();
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.SENSORS_MANUFACTURER, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the type of the sensor
	 * @param s Sensor
	 * @return string the sensor type
	 */
	public String getType(Sensor s) {
		String valueType = "N/A";
		try {
			int type = s.getType();
			switch (type) {
				case Sensor.TYPE_ACCELEROMETER:
					valueType = "ACCELEROMETER";
					break;
				case Sensor.TYPE_GRAVITY:
					valueType = "GRAVITY";
					break;
				case Sensor.TYPE_GYROSCOPE:
					valueType = "GYROSCOPE";
					break;
				case Sensor.TYPE_LINEAR_ACCELERATION:
					valueType = "LINEAR ACCELERATION";
					break;
				case Sensor.TYPE_MAGNETIC_FIELD:
					valueType = "MAGNETIC FIELD";
					break;
				case Sensor.TYPE_PRESSURE:
					valueType = "PRESSURE";
					break;
				case Sensor.TYPE_PROXIMITY:
					valueType = "PROXIMITY";
					break;
				case Sensor.TYPE_ROTATION_VECTOR:
					valueType = "ROTATION VECTOR";
					break;
				default:
					valueType = "Unknow";
					break;
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.SENSORS_TYPE, ex.getMessage()));
		}
		return valueType;
	}

	/**
	 * Get the power of the sensor
	 * @param s Sensor
	 * @return string the power used by the sensor while in use
	 */
	public String getPower(Sensor s) {
		String value = "N/A";
		try {
			value = String.valueOf(s.getPower());
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.SENSORS_POWER, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the version of the sensor's module
	 * @param s Sensor
	 * @return string the version
	 */
	public String getVersion(Sensor s) {
		String value = "N/A";
		try {
			value = String.valueOf(s.getVersion());
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.SENSORS_VERSION, ex.getMessage()));
		}
		return value;
	}
}
