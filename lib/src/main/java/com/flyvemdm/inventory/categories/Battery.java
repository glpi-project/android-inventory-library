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
	private String level = "";
	private String voltage = "";
	private String temperature = "";
	private String status = "";
	private String health = "";
	private String technology = "";

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		return (!super.equals(obj));
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 89 * hash + (this.level != null ? this.level.hashCode() : 0);
		hash = 89 * hash + (this.voltage != null ? this.voltage.hashCode() : 0);
		hash = 89 * hash + (this.temperature != null ? this.temperature.hashCode() : 0);
		hash = 89 * hash + (this.status != null ? this.status.hashCode() : 0);
		hash = 89 * hash + (this.health != null ? this.health.hashCode() : 0);
		hash = 89 * hash + (this.technology != null ? this.technology.hashCode() : 0);
		return hash;
	}

	/**
	 * This constructor trigger BroadcastReceiver to get all the information about Battery
	 * @param xCtx Context where this class work
	 */
	public Battery(Context xCtx) {
		super(xCtx);

		// Trigger BroadcastReceiver
		xCtx.registerReceiver(this.myBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		this.myBatteryReceiver.onReceive(xCtx, new Intent(Intent.ACTION_BATTERY_CHANGED));
	}

	/**
	 *  This BroadcastReceiver load the information of the component
	 */
	private BroadcastReceiver myBatteryReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {

				level = String.valueOf(intent.getIntExtra("level", 0)) + "%";

				voltage = String
						.valueOf((float) intent.getIntExtra("voltage", 0) / 1000)
						+ "V";

				temperature = String.valueOf((float) intent.getIntExtra(
						"temperature", 0) / 10)
						+ "c";

				technology = intent.getStringExtra("technology");

				// get battery status
				int intstatus = intent.getIntExtra("status",
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
				int inthealth = intent.getIntExtra("health",
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

				if(!level.equals("0%")) {
					// Load the information
					Category c = new Category("BATTERIES");
					c.put("CHEMISTRY", technology);
					c.put("TEMPERATURE", temperature);
					c.put("VOLTAGE", voltage);
					c.put("LEVEL", level);
					c.put("HEALTH", health);
					c.put("STATUS", status);
					Battery.this.add(c);
				}

			}
		}

	};
}
