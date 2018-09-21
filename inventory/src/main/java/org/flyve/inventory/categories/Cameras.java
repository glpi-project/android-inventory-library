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
 *  @author    Ivan del Pino    - <idelpino@teclib.com>
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
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;

import org.flyve.inventory.FILog;

import java.util.ArrayList;
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
                for (int index = 0; index < count; index++) {
                    Category c = new Category("CAMERAS", "cameras");
                    c.put("RESOLUTIONS", new CategoryValue(getResolutions(index), "RESOLUTIONS", "resolutions"));
                    CameraCharacteristics chars = getCharacteristics(xCtx, index);
                    if (chars != null) {
                        c.put("LENSFACING", new CategoryValue(getFacingState(chars), "LENSFACING", "lensfacing"));
                        c.put("IMAGEFORMAT", new CategoryValue(getCategoryImageFormat(chars)));
                        c.put("FLASHUNIT", new CategoryValue(getFlashUnit(chars), "FLASHUNIT", "flashunit"));
                    }
                    this.add(c);
                }
            }
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }
    }

    /**
     * Get info characteristics of the camera
     * @param xCtx
     * @param index number of the camera
     * @return CameraCharacteristics type object
     */
    public CameraCharacteristics getCharacteristics(Context xCtx, int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CameraManager manager = (CameraManager) xCtx.getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
            try {
                assert manager != null;
                String cameraId = manager.getCameraIdList()[index];
                return manager.getCameraCharacteristics(cameraId);
            } catch (CameraAccessException e) {
                FILog.e(e.getMessage());
            } catch (NullPointerException e) {
                FILog.e(e.getMessage());
            }
        }
        return null;
    }

    /**
     * Get direction the camera faces relative to device screen
     * @param characteristics
     * @return String The camera device faces the same direction as the device's screen
     */
    public String getFacingState(CameraCharacteristics characteristics) {
        String value = "N/A";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
            if (facing != null) {
                switch (facing) {
                    case 0:
                        value = "FRONT";
                        break;
                    case 1:
                        value = "BACK";
                        break;
                    case 2:
                        value = "EXTERNAL";
                        break;
                }
            }
        }
        return value;
    }

    /**
     * Version information about the camera device
     * @param characteristics
     * @return String manufacturers camera
     */
    public String getManufacturer(CameraCharacteristics characteristics) {
        String value = "N/A";

        return value;
    }

    /**
     * Whether this camera device has a flash unit.
     * @param characteristics
     * @return String 0 no available, 1 is available
     */
    public String getFlashUnit(CameraCharacteristics characteristics) {
        String value = "N/A";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Boolean bool = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
            return bool != null ? bool ? "1" : "0" : "-1";
        }
        return value;
    }

    public Category getCategoryImageFormat(CameraCharacteristics chars) {
        Category category = new Category("IMAGEFORMAT", "imageformat");
        for (String imageFormat : getImageFormat(chars)) {
            category.put("FORMAT", new CategoryValue(imageFormat, "FORMAT", "format"));
        }
        return category;
    }

    /**
     * version information about the camera device
     * The available stream configurations that this camera device supports
     * @param characteristics
     * @return String The camera device faces the same direction as the device's screen
     */
    public ArrayList<String> getImageFormat(CameraCharacteristics characteristics) {
        ArrayList<String> types = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            StreamConfigurationMap configurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (configurationMap != null) {
                int[] outputFormats = configurationMap.getOutputFormats();
                if (outputFormats != null) {
                    for (int value : outputFormats) {
                        String type = typeFormat(value);
                        if (type != null) {
                            types.add(type);
                        }
                    }
                }
            }
        }
        return types;
    }

    public String typeFormat(int i) {
        switch (i) {
            case 4:
                return "RGB_565";
            case 16:
                return "NV16";
            case 17:
                return "NV21";
            case 20:
                return "YUY2";
            case 32:
                return "RAW_SENSOR";
            case 35:
                return "YUV_420_888";
            case 37:
                return "RAW10";
            case 39:
                return "YUV_422_888";
            case 256:
                return "JPEG";
            case 842094169:
                return "YV12";
            default:
                return null;
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
