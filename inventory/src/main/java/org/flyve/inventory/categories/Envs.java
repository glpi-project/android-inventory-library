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

import org.flyve.inventory.FlyveLog;

import java.util.Map;

/**
 * This class get all the information of the Environment
 */
public class Envs extends Categories {

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
	private static final long serialVersionUID = -6210390594988309754L;

    /**
     * This constructor load the context and the Environment information
     * @param xCtx Context where this class work
     */
	public Envs(Context xCtx) {
        super(xCtx);

        try {
            //get all envs vars
            Map<String, String> envs = System.getenv();
            for (Map.Entry<String, String> entry : envs.entrySet()) {
                Category c = new Category("ENVS", "envs");
                c.put("KEY", new CategoryValue(entry.getKey(), "KEY", "key"));
                c.put("VAL", new CategoryValue(envs.get(entry.getKey()), "VAL", "Value"));
                this.add(c);
            }
        } catch (Exception ex) {
            FlyveLog.e(ex.getMessage());
        }
    }
}
