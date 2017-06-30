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

import android.app.Service;
import android.content.Context;
import android.location.LocationManager;
import android.location.LocationProvider;

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

    /**
     * This constructor load the context and the Location Providers information
     * @param xCtx Context where this class work
     */
    public LocationProviders(Context xCtx) {
        super(xCtx);

        LocationManager lLocationMgr = (LocationManager) xCtx.getSystemService(Service.LOCATION_SERVICE);
        
        List<String> lProvidersName = lLocationMgr.getAllProviders(); 
        
        for (String p : lProvidersName) {
            Category c = new Category("LOCATION_PROVIDERS");

            LocationProvider lProvider = lLocationMgr.getProvider(p);
            c.put("NAME" , getName(lProvider));

            this.add(c);
        }
    }

    public String getName(LocationProvider lProvider) {
        return lProvider.getName();
    }
}
