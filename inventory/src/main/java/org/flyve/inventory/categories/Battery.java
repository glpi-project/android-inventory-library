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

package org.flyve.inventory.categories;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

import org.flyve.inventory.FILog;

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
    private String capacity = "";

	/**
     * Indicates whether some other object is "equal to" this one
     * @param Object obj the reference object with which to compare.
     * @return boolean true if the object is the same as the one given in argument
     */
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

    /**
     * Returns a hash code value for the object
     * @return int a hash code value for the object
     */
	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 89 * hash + (this.level != null ? this.level.hashCode() : 0);
		hash = 89 * hash + (this.voltage != null ? this.voltage.hashCode() : 0);
		hash = 89 * hash + (this.temperature != null ? this.temperature.hashCode() : 0);
		hash = 89 * hash + (this.status != null ? this.status.hashCode() : 0);
		hash = 89 * hash + (this.health != null ? this.health.hashCode() : 0);
		hash = 89 * hash + (this.technology != null ? this.technology.hashCode() : 0);
		hash = 89 * hash + (this.capacity != null ? this.capacity.hashCode() : 0);
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

                capacity = String.valueOf(getBatteryVoltage(context));

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

				try {
                    if (!level.equals("0%")) {
                        // Load the information
                        Category c = new Category("BATTERIES", "batteries");
                        c.put("CHEMISTRY", new CategoryValue(technology, "CHEMISTRY", "chemistry"));
                        c.put("TEMPERATURE", new CategoryValue(temperature, "TEMPERATURE", "temperature"));
                        c.put("VOLTAGE", new CategoryValue(voltage, "VOLTAGE", "voltage"));
                        c.put("LEVEL", new CategoryValue(level, "LEVEL", "level"));
                        c.put("HEALTH", new CategoryValue(health, "HEALTH", "health"));
                        c.put("STATUS", new CategoryValue(status, "STATUS", "status"));
                        c.put("CAPACITY", new CategoryValue(capacity, "CAPACITY", "capacity"));
                        Battery.this.add(c);
                    }
                } catch (Exception ex) {
                    FILog.e(ex.getMessage());
                }

			}
		}

    };

    private long getBatteryVoltage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            Integer chargeCounter = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
            Integer capacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

            if(chargeCounter == Integer.MIN_VALUE || capacity == Integer.MIN_VALUE)
                return 0;

            return (chargeCounter/capacity) *100;
        } else {
            Object mPowerProfile;
            double batteryCapacity = 0;
            final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

            try {
                mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                        .getConstructor(Context.class)
                        .newInstance(context);

                batteryCapacity = (double) Class
                        .forName(POWER_PROFILE_CLASS)
                        .getMethod("getBatteryCapacity")
                        .invoke(mPowerProfile);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return (long) batteryCapacity;
        }
    }
}
