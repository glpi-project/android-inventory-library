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
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class get all the information of the Controllers
 */
public class Modems extends Categories {

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
	private static final long serialVersionUID = 6791259866128400637L;
    private final Context context;

    /**
     * This constructor trigger get all the information about Controllers
     * @param xCtx Context where this class work
     */
	public Modems(Context xCtx) {
        super(xCtx);
        context = xCtx;

        try {
            ArrayList<String> imeiList;
            imeiList = getIMEI();

            for (String imei :imeiList) {
                if(imei != null && !imei.equalsIgnoreCase("N/A")){
                    Category c = new Category("MODEMS", "modems");
                    c.put("IMEI", new CategoryValue(imei, "IMEI", "imei"));
                    this.add(c);
                }
            }

        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.MODEMS, ex.getMessage()));
        }
    }

    /**
     * Get the imei
     * @return list of the IMEI to Simcard
     */
    public ArrayList<String> getIMEI() {
        ArrayList<String> imeiList = new ArrayList<>();
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                assert subscriptionManager != null;
                List<SubscriptionInfo> multipleSim = subscriptionManager.getActiveSubscriptionInfoList();
                if (multipleSim != null && multipleSim.size() > 0) {
                    for (int i = 0; i < multipleSim.size(); i++) {
                        if (telephonyManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if(telephonyManager.getDeviceId(i) != null){
                                imeiList.add(telephonyManager.getDeviceId(i));
                            }

                        }
                    }
                } else {
                    imeiList.add("N/A");
                }
            } else {
                String imei = telephonyManager.getDeviceId();
                imeiList.add(imei != null ? imei : "N/A");
            }
        } catch (Exception ex) {
            imeiList.add("N/A");
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.MODEMS_IMEI, ex.getMessage()));
        }
        return imeiList;
    }

}
