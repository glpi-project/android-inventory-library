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
 *  @author    Rafael Hernandez - <rhernandez@teclib.com>
 *  @copyright Copyright Teclib. All rights reserved.
 *  @copyright Copyright FusionInventory.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.content.Context;
import android.hardware.Camera;

import org.flyve.inventory.FILog;

import java.util.List;

/**
 * This class get all the information of the Cameras
 */
public class Cameras
        extends Categories {

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

    /**
     * This constructor trigger get all the information about Cameras
     * @param xCtx Context where this class work
     */
	public Cameras(Context xCtx) {
        super(xCtx);

        // Get resolutions of the camera
        // Work on Android SDK Version >= 5
        try {
            int count = Camera.getNumberOfCameras();
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    Category c = new Category("CAMERAS", "cameras");
                    c.put("RESOLUTIONS", new CategoryValue(getResolutions(i), "RESOLUTIONS", "resolutions"));
                    this.add(c);
                }
            }
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }

    /**
     * Get the camera resolutions
     * @param index
     * @return string with the width and height
     */
    public String getResolutions(int index) {
        Camera cam = Camera.open(index);
        if(cam != null) {
            Camera.Parameters params = cam.getParameters();
            List<Camera.Size> list;

            list = params.getSupportedPictureSizes();
            int width = 0;
            int height = 0;
            for (Camera.Size size : list) {
                if ((size.width * size.height) > (width * height)) {
                    width = size.width;
                    height = size.height;
                }
            }
            cam.release();

            // cam resolution width x height
            return String.format("%dx%d", width, height);
        } else {
         return "";
        }
    }


}
