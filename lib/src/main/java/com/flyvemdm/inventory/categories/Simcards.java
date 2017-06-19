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
import android.telephony.TelephonyManager;

/**
 * This class get all the information of the Simcards Devices
 */
public class Simcards extends Categories {

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
    private static final long serialVersionUID = -5532129156981574844L;

    private TelephonyManager mTM;

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
        hash = 89 * hash + (this.mTM != null ? this.mTM.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the context and the Simcards information
     * @param xCtx Context where this class work
     */
    public Simcards(Context xCtx) {
        super(xCtx);
        
        mTM = (TelephonyManager) xCtx.getSystemService(Context.TELEPHONY_SERVICE);

        /*
         * Starting SimCards Informations retrieval
         */
        Category c = new Category("SIMCARDS");

        c.put("COUNTRY", getCountry());
        c.put("OPERATOR_CODE", getOperatorCode());
        c.put("OPERATOR_NAME", getOperatorName());
        c.put("SERIAL", getSerial());
        c.put("STATE", getState());
        c.put("LINE_NUMBER", getLineNumber());
        c.put("SUBSCRIBER_ID", getSubscriberId());
        
        this.add(c);
    }

    public String getCountry() {
        return mTM.getSimCountryIso();
    }

    public String getOperatorCode() {
        return mTM.getSimOperator();
    }

    public String getOperatorName() {
        return mTM.getSimOperatorName();
    }

    public String getSerial() {
        return mTM.getSimSerialNumber();
    }

    public String getState() {
        String mState = "";
        switch(mTM.getSimState()) {
            case TelephonyManager.SIM_STATE_ABSENT:
                mState = "SIM_STATE_ABSENT";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                mState = "SIM_STATE_NETWORK_LOCKED";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                mState = "SIM_STATE_PIN_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                mState = "SIM_STATE_PUK_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_READY:
                mState = "SIM_STATE_READY";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                mState = "SIM_STATE_UNKNOWN";
                break;
            default:
                mState = "Unknow";
                break;
        }
        return mState;
    }

    public String getLineNumber() {
        return mTM.getLine1Number();
    }

    public String getSubscriberId() {
        return mTM.getSubscriberId();
    }
}
