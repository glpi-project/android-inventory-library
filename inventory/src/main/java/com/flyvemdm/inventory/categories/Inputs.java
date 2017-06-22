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

import android.content.Context;
import android.content.res.Configuration;

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
		hash = 89 * hash + (this.config != null ? this.config.hashCode() : 0);
		return hash;
	}

	/**
	 * This constructor load the context and the Inputs information
	 * @param xCtx Context where this class work
	 */
	public Inputs(Context xCtx) {
		super(xCtx);

		config = xCtx.getResources().getConfiguration();

		Category c = new Category("INPUTS");

		c.put("KEYBOARD", getKeyboard());
		c.put("TOUCHSCREEN", getTouchscreen());

		this.add(c);
	}

	public String getKeyboard() {

		String val;

		switch (config.keyboard) {
			case Configuration.KEYBOARD_QWERTY:
			case Configuration.KEYBOARD_12KEY:
				val = "YES";
				break;
			case Configuration.KEYBOARD_NOKEYS:
			default:
				val = "NO";
				break;
		}

		return val;
	}

	public String getTouchscreen() {

		String val = "";
		switch (config.touchscreen) {
			case Configuration.TOUCHSCREEN_STYLUS:
				val = "STYLUS";
				break;
			case Configuration.TOUCHSCREEN_FINGER:
				val = "FINGER";
				break;
			case  Configuration.TOUCHSCREEN_NOTOUCH:
				val = "NOTOUCH";
				break;
			default:
				break;
		}

		return val;
	}
}
