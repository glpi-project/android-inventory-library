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
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import com.flyvemdm.inventory.FILog;

/**
 * This class get all the information of the Network
 */
public class PhoneStatus extends Categories {

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
    private static final long serialVersionUID = 872718741270132229L;

    /**
     * This constructor load the context and the PhoneStatus information
     * @param xCtx Context where this class work
     */
    public PhoneStatus(Context xCtx) {
        super(xCtx);

        //TODO Solved the problem Thread inside other Thread to make PhoenStatus works
        // The problem here is with a the PhoneStateListener and the
        // AsyncTask we got a Thread inside other Thread

        final Category c = new Category("PHONE_STATUS");

        TelephonyManager telMng = (TelephonyManager)xCtx.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            telMng.listen(new PhoneStateListener() {
                @Override
                public void onServiceStateChanged(ServiceState serviceState) {
                    super.onServiceStateChanged(serviceState);
                    String phoneState;

                    switch (serviceState.getState()) {
                        case ServiceState.STATE_EMERGENCY_ONLY:
                            phoneState = "STATE_EMERGENCY_ONLY";
                            break;
                        case ServiceState.STATE_IN_SERVICE:
                            phoneState = "STATE_IN_SERVICE";
                            break;
                        case ServiceState.STATE_OUT_OF_SERVICE:
                            phoneState = "STATE_OUT_OF_SERVICE";
                            break;
                        case ServiceState.STATE_POWER_OFF:
                            phoneState = "STATE_POWER_OFF";
                            break;
                        default:
                            phoneState = "Unknown";
                            break;
                    }

                    c.put("STATE", phoneState);
                    c.put("OPERATOR_ALPHA", serviceState.getOperatorAlphaLong());
                    c.put("OPERATOR_NUMERIC", serviceState.getOperatorNumeric());
                    c.put("ROAMING", Boolean.valueOf(serviceState.getRoaming()).toString());
                    c.put("NETWORK_SELECTION", serviceState.getIsManualSelection() ? "MANUAL" : "AUTO");

                    PhoneStatus.this.add(c);
                }
            }, PhoneStateListener.LISTEN_SERVICE_STATE);
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }
}
