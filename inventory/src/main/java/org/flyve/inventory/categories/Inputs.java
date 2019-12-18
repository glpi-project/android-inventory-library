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
import android.content.res.Configuration;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

/**
 * This class get all the information of the Inputs
 */
public class Inputs extends Categories {

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
	private Configuration config;
	private final Context context;

	/**
	 * Indicates whether some other object is "equal to" this one
	 * @param Object obj the reference object with which to compare
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
		hash = 89 * hash + (this.config != null ? this.config.hashCode() : 0);
		return hash;
	}

	/**
	 * This constructor load the context and the Inputs information
	 * @param xCtx Context where this class work
	 */
	public Inputs(Context xCtx) {
		super(xCtx);

		context = xCtx;

		config = context.getResources().getConfiguration();

		try {

			if (getKeyboard()) {
				Category c = new Category("INPUTS", "inputs");

				c.put("CAPTION", new CategoryValue("Keyboard", "CAPTION", "caption"));
				c.put("DESCRIPTION", new CategoryValue("Keyboard", "DESCRIPTION", "description"));
				c.put("TYPE", new CategoryValue("Keyboard", "TYPE", "type"));

				this.add(c);
			}

			Category c = new Category("INPUTS", "inputs");

			c.put("CAPTION", new CategoryValue("Touch Screen", "CAPTION", "caption"));
			c.put("DESCRIPTION", new CategoryValue("Touch Screen", "DESCRIPTION", "description"));
			c.put("TYPE", new CategoryValue(getTouchscreen(), "TYPE", "type"));

			this.add(c);
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.INPUTS, ex.getMessage()));
		}
	}

	/**
	 * Get the keyboard
	 * @return string if the device has a hardware keyboard
	 */
	public Boolean getKeyboard() {
		Boolean val = false;

		try {
			switch (config.keyboard) {
				case Configuration.KEYBOARD_QWERTY:
				case Configuration.KEYBOARD_12KEY:
					val = true;
					break;
				case Configuration.KEYBOARD_NOKEYS:
				default:
					val = false;
					break;
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.INPUTS_KEY_BOARD, ex.getMessage()));
		}

		return val;
	}

	/**
	 * Get the touchscreen
	 * @return string the type of screen the device has
	 */
	public String getTouchscreen() {
		String val = "N/A";
		try {
			switch (config.touchscreen) {
				case Configuration.TOUCHSCREEN_STYLUS:
					val = "STYLUS";
					break;
				case Configuration.TOUCHSCREEN_FINGER:
					val = "FINGER";
					break;
				case Configuration.TOUCHSCREEN_NOTOUCH:
					val = "NOTOUCH";
					break;
				default:
					val = "N/A";
					break;
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.INPUTS_TOUCH_SCREEN, ex.getMessage()));
		}
		return val;
	}
}
