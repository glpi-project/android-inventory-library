/**
 * FusionInventory
 *
 * Copyright (C) 2010-2017 by the FusionInventory Development Team.
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

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

/**
 * This class get all the information of the Bluetooth
 */
public class Bluetooth extends Categories {

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
    private static final long serialVersionUID = 3252750764653173048L;

    private BluetoothAdapter adapter;

    /**
     * This constructor get all the information about Bluetooth
     * @param xCtx Context where this class work
     */
    public Bluetooth(Context xCtx) {
        super(xCtx);

        adapter = BluetoothAdapter.getDefaultAdapter();

        if(adapter != null) {
            Category c = new Category(mCtx, "BLUETOOTH_ADAPTER");

            // The hardware address of the local Bluetooth adapter.
            c.put("HMAC", getHardware_address());

            // This name is visible to remote Bluetooth devices.
            c.put("NAME", getName());
            this.add(c);
        }
    }

    public String getHardware_address() {
        return adapter.getAddress();
    }

    public String getName() {
        return adapter.getName();
    }

}
