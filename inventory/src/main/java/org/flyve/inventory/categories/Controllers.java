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

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.FlyveLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class get all the information of the Controllers
 */
public class Controllers extends Categories {

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
	public Controllers(Context xCtx) {
        super(xCtx);

        context = xCtx;

        try {
            ArrayList<String> drivers = getDrivers();
            if (drivers.size() > 0) {
                for (int i = 0; i < drivers.size(); i++) {
                    Category c = new Category("CONTROLLERS", "controllers");
                    c.put("DRIVER", new CategoryValue(drivers.get(i), "DRIVER", "driver"));
                    this.add(c);
                }
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.CONTROLLERS, ex.getMessage()));
        }
    }

    /**
     * Get the name Driver
     * @return string name Driver
     */
    public ArrayList<String> getDrivers() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            ArrayList<String> arrayList2 = new ArrayList<>();
            File file = new File("/sys/bus/platform/drivers/");
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        String name = file2.getName();
                        String a = filterEmptyFile(file2);
                        if (a == null || a.isEmpty()) {
                            arrayList2.add(name);
                        } else {
                            arrayList.add(name);
                        }
                    }
                }
                if (arrayList.isEmpty()) {
                    arrayList.addAll(arrayList2);
                }
                Collections.sort(arrayList);
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.CONTROLLERS_DRIVERS, ex.getMessage()));
        }
        return arrayList;
    }

    private String filterEmptyFile(File file) {
        String value = "N/A";
        try {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return value;
            }
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    String name = file2.getName();
                    if (!("bind".equals(name) || "unbind".equals(name) || "uevent".equals(name))) {
                        value = name;
                    }
                }
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.CONTROLLERS_FILE, ex.getMessage()));
        }
        return value;
    }

}
