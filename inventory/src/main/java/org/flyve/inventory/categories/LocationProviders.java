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

import android.app.Service;
import android.content.Context;
import android.location.LocationManager;
import android.location.LocationProvider;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

import java.util.List;

/**
 * This class get all the information of the Location Providers
 */
public class LocationProviders extends Categories {

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
    private static final long serialVersionUID = 6066226866162586918L;
    private final Context context;

    /**
     * This constructor load the context and the Location Providers information
     * @param xCtx Context where this class work
     */
    public LocationProviders(Context xCtx) {
        super(xCtx);

        context = xCtx;

        try {
            LocationManager lLocationMgr = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);

            if (lLocationMgr != null) {
                List<String> lProvidersName = lLocationMgr.getAllProviders();

                for (String p : lProvidersName) {
                    Category c = new Category("LOCATION_PROVIDERS", "locationProviders");

                    LocationProvider lProvider = lLocationMgr.getProvider(p);
                    c.put("NAME", new CategoryValue(getName(lProvider), "NAME", "name"));

                    this.add(c);
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.LOCATION, ex.getMessage()));
        }
    }

    /**
     * Get the provider name
     * @param lProvider
     * @return string the name of the provider
     */
    public String getName(LocationProvider lProvider) {
        String value  = "N/A";
        try {
            value = lProvider.getName();
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.LOCATION_NAME, ex.getMessage()));
        }
        return value;
    }
}
