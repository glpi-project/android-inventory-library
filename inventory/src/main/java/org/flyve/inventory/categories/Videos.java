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
import android.graphics.Point;
import android.view.WindowManager;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;

/**
 * This class get all the information of the Video
 */
public class Videos extends Categories {

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
    private static final long serialVersionUID = 6953895287405000489L;
    private Context context;

    /**
     * Indicates whether some other object is "equal to" this one
     * @param Object obj the reference object with which to compare
     * @return boolean true if the object is the same as the one given in argument
     */
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

    /**
     * Returns a hash code value for the object
     * @return int a hash code value for the object
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.context != null ? this.context.hashCode() : 0);
        return hash;
    }

    /**
     *  This constructor load the context and the Video information
     * @param xCtx Context where this class work
     */
    public Videos(Context xCtx) {
        super(xCtx);
        this.context = xCtx;

        try {
            Category c = new Category("VIDEOS", "videos");
            c.put("RESOLUTION", new CategoryValue(getResolution(), "RESOLUTION", "resolution"));
            this.add(c);
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.VIDEOS, ex.getMessage()));
        }
    }

    /**
     * Get the video resolutions
     * @return string the width and height
     */
    public String getResolution() {
        String value = "N/A";
        try {
            WindowManager lWinMgr = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
            Point size = new Point();
            lWinMgr.getDefaultDisplay().getSize(size);

            int width = size.x;
            int height = size.y;

            value = String.format("%dx%d", width, height);
            return value;
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.VIDEOS_RESOLUTION, ex.getMessage()));
        }
        return value;
    }
}
