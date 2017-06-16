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

 package com.flyvemdm.inventory.categories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * This class get all the information of the baterry like level, voltage, temperature, status, health, technology
 * The constructor of the class trigger a BroadcastReceiver with the information
 */

public class Battery extends Categories {

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
	private static final long serialVersionUID = -4096347994131285426L;

	// Properties of this component
	private String level = "Unknown";
	private String voltage = "Unknown";
	private String temperature = "Unknown";
	private String status = "Unknown";
	private String health = "Unknown";
	private String technology = "Unknown";

	/**
	 * This constructor trigger BroadcastReceiver to get all the information about Battery
	 * @param xCtx Context where this class work
	 */
	public Battery(Context xCtx) {
		super(xCtx);

		// Trigger BroadcastReceiver
		xCtx.registerReceiver(this.myBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		this.myBatteryReceiver.onReceive(xCtx, new Intent(Intent.ACTION_BATTERY_CHANGED));

		// Load the information
		Category c  = new Category(xCtx, "BATTERIES");
		c.put("CHEMISTRY", technology);
		c.put("TEMPERATURE", temperature);
		c.put("VOLTAGE", voltage);
		c.put("LEVEL", level);
		c.put("HEALTH", health);
		c.put("STATUS", status);
		this.add(c);
	}


	/**
	 *  This BroadcastReceiver load the information of the component
	 */
	private BroadcastReceiver myBatteryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {

			if (arg1.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

				level = String.valueOf(arg1.getIntExtra("level", 0)) + "%";

				voltage = String
						.valueOf((float) arg1.getIntExtra("voltage", 0) / 1000)
						+ "V";

				temperature = String.valueOf((float) arg1.getIntExtra(
						"temperature", 0) / 10)
						+ "c";

				technology = arg1.getStringExtra("technology");

				// get battery status
				int intstatus = arg1.getIntExtra("status",
						BatteryManager.BATTERY_STATUS_UNKNOWN);
				if (intstatus == BatteryManager.BATTERY_STATUS_CHARGING) {
					status = "Charging";
				} else if (intstatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
					status = "Dis-charging";
				} else if (intstatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
					status = "Not charging";
				} else if (intstatus == BatteryManager.BATTERY_STATUS_FULL) {
					status = "Full";
				} else {
					status = "Unknown";
				}

				// get battery health
				int inthealth = arg1.getIntExtra("health",
						BatteryManager.BATTERY_HEALTH_UNKNOWN);
				if (inthealth == BatteryManager.BATTERY_HEALTH_GOOD) {
					health = "Good";
				} else if (inthealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
					health = "Over Heat";
				} else if (inthealth == BatteryManager.BATTERY_HEALTH_DEAD) {
					health = "Dead";
				} else if (inthealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
					health = "Over Voltage";
				} else if (inthealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
					health = "Unspecified Failure";
				} else {
					health = "Unknown";
				}

			}
		}

	};
}
