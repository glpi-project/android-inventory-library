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
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.FlyveLog;

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
        try {

        final Category c = new Category("PHONE_STATUS", "phoneStatus");

        TelephonyManager telMng = (TelephonyManager)xCtx.getSystemService(Context.TELEPHONY_SERVICE);
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

                    c.put("STATE", new CategoryValue(phoneState, "STATE", "state"));
                    c.put("OPERATOR_ALPHA", new CategoryValue(serviceState.getOperatorAlphaLong(), "OPERATOR_ALPHA", "operatorAlpha"));
                    c.put("OPERATOR_NUMERIC", new CategoryValue(serviceState.getOperatorNumeric(), "OPERATOR_NUMERIC", "operatorNumeric"));
                    c.put("ROAMING", new CategoryValue(Boolean.valueOf(serviceState.getRoaming()).toString(), "ROAMING", "roaming"));
                    c.put("NETWORK_SELECTION", new CategoryValue(serviceState.getIsManualSelection() ? "NETWORK_SELECTION" : "AUTO", "NETWORK_SELECTION", "networkSelection"));

                    PhoneStatus.this.add(c);
                }
            }, PhoneStateListener.LISTEN_SERVICE_STATE);
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(xCtx, CommonErrorType.PHONE_STATUS, ex.getMessage()));
        }
    }
}
