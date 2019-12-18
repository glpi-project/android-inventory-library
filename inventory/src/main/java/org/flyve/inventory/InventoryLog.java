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

package org.flyve.inventory;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * This is a Utils class grapper for Log
 */
public final class InventoryLog {

    // This is the tag to search on console
    static final String TAG = "InventoryLibrary";

    /**
     * private constructor to prevent instances of this class
     */
    private InventoryLog() {
    }

    /**
     * Sends a DEBUG log message
     * @param message to log
     */
    public static void d(String message) {
        if(message != null) {
            Log.d(TAG, message);
        }
    }

    /**
     * Sends a VERBOSE log message
     * @param message to log
     */
    public static void v(String message) {
        if(message != null) {
            Log.v(TAG, message);
        }
    }

    /**
     * Sends an INFO log message
     * @param message to log
     */
    public static void i(String message) {
        if(message != null) {
            Log.i(TAG, message);
        }
    }

    /**
     * Sends an ERROR log message
     * @param message to log
     */
    public static void e(String message) {
        if(message != null) {
            Log.e(TAG, message);
        }
    }

    /**
     * Sends a low level calling log
     * @param obj the name of the class
     * @param msg the log message
     * @param level the priority/type of the log message
     */
    public static void log(Object obj, String msg, int level) {
        String final_msg = String.format("[%s] %s", obj.getClass().getName(), msg);
        Log.println(level, "InventoryAgent", final_msg);
    }

    public static String getMessage(Context context, int type, String message) {
        Resources resource = context.getResources();
        return resource.getString(R.string.error_message_with_number, String.valueOf(type), message);
    }

    public static String getMessage(String type, String message) {
        String resource = Resources.getSystem().getString(R.string.error_message_with_number);
        return String.format(resource, type, message);
    }
}