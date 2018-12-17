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
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.FlyveLog;

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
	private final Intent batteryIntent;
	private final Context context;

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
		hash = 89 * hash + (this.batteryIntent != null ? this.batteryIntent.hashCode() : 0);
		return hash;
	}

	/**
	 * This constructor trigger BroadcastReceiver to get all the information about Battery
	 * @param xCtx Context where this class work
	 */
	public Battery(Context xCtx) {
		super(xCtx);
		context = xCtx;

		IntentFilter batteryIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryIntent = context.registerReceiver(null, batteryIntentFilter);

		try {
			if (batteryIntent != null) {
				if (!getLevel().equals("0%")) {
					// Load the information
					Category c = new Category("BATTERIES", "batteries");
					c.put("CHEMISTRY", new CategoryValue(getTechnology(), "CHEMISTRY", "chemistry"));
					c.put("TEMPERATURE", new CategoryValue(getTemperature(), "TEMPERATURE", "temperature"));
					c.put("VOLTAGE", new CategoryValue(getVoltage(), "VOLTAGE", "voltage"));
					c.put("LEVEL", new CategoryValue(getLevel(), "LEVEL", "level"));
					c.put("HEALTH", new CategoryValue(getBatteryHealth(), "HEALTH", "health"));
					c.put("STATUS", new CategoryValue(getBatteryStatus(), "STATUS", "status"));
					c.put("CAPACITY", new CategoryValue(getCapacity(), "CAPACITY", "capacity"));
					this.add(c);
				}
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY, ex.getMessage()));
		}
	}

	public String getTechnology() {
		String technology = "N/A";
		try {
			batteryIntent.getStringExtra("technology");
			return technology;
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_TECHNOLOGY, ex.getMessage()));
		}
		return technology;
	}

	public String getTemperature() {
		String temperature = "N/A";
		try {
			float extra = batteryIntent.getIntExtra("temperature", 0);
			temperature = String.valueOf(extra / 10) + "c";
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_TEMPERATURE, ex.getMessage()));
		}
		return temperature;
	}

	public String getVoltage() {
		String voltage  = "N/A";
		try {
			voltage = String.valueOf((float) batteryIntent.getIntExtra("voltage", 0) / 1000) + "V";
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_VOLTAGE, ex.getMessage()));
		}
		return voltage;
	}

	public String getLevel() {
		String level = "N/A";
		try {
			level = String.valueOf(batteryIntent.getIntExtra("level", 0)) + "%";
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_LEVEL, ex.getMessage()));
		}
		return level;
	}

	public String getBatteryHealth() {
		String health = "N/A";
		try {
			int intHealth = batteryIntent.getIntExtra("health",
					BatteryManager.BATTERY_HEALTH_UNKNOWN);
			if (intHealth == BatteryManager.BATTERY_HEALTH_GOOD) {
				health = "Good";
			} else if (intHealth == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
				health = "Over Heat";
			} else if (intHealth == BatteryManager.BATTERY_HEALTH_DEAD) {
				health = "Dead";
			} else if (intHealth == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
				health = "Over Voltage";
			} else if (intHealth == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
				health = "Unspecified Failure";
			} else {
				health = "Unknown";
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_HEALTH, ex.getMessage()));
		}
        return health;
	}

	public String getBatteryStatus() {
		String status  = "N/A";
		try {
			int intStatus = batteryIntent.getIntExtra("status",
					BatteryManager.BATTERY_STATUS_UNKNOWN);
			if (intStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
				status = "Charging";
			} else if (intStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
				status = "Dis-charging";
			} else if (intStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
				status = "Not charging";
			} else if (intStatus == BatteryManager.BATTERY_STATUS_FULL) {
				status = "Full";
			} else {
				status = "Unknown";
			}
		}  catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_STATUS, ex.getMessage()));
		}
        return status;
	}

	public String getCapacity() {
		String capacityValue = "N/A";
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
				assert mBatteryManager != null;
				Integer capacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

				if (capacity == Integer.MIN_VALUE)
					return String.valueOf(0);

				capacityValue = String.valueOf(capacity);
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

				capacityValue = String.valueOf(batteryCapacity);
			}
		}  catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.BATTERY_CAPACITY, ex.getMessage()));
		}
		return capacityValue;
	}

}
