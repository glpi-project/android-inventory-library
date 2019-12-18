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
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Size;
import android.util.SizeF;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;
import org.flyve.inventory.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
    private final String cameraVendors;
    private final Context context;

    /**
     * This constructor trigger get all the information about Cameras
     * @param xCtx Context where this class work
     */
	public Cameras(Context xCtx) {
        super(xCtx);
        context = xCtx;

        cameraVendors = Utils.loadJSONFromAsset(xCtx, "camera_vendors.json");

        // Get resolutions of the camera
        // Work on Android SDK Version >= 5
        try {
            int count = Camera.getNumberOfCameras();
            if (count > 0) {
                for (int index = 0; index < count; index++) {
                    Category c = new Category("CAMERAS", "cameras");
                    CameraCharacteristics chars = getCharacteristics(xCtx, index);
                    if (chars != null) {
                        c.put("RESOLUTION", new CategoryValue(getResolution(chars), "RESOLUTION", "resolution"));
                        c.put("LENSFACING", new CategoryValue(getFacingState(chars), "LENSFACING", "lensfacing"));
                        c.put("FLASHUNIT", new CategoryValue(getFlashUnit(chars), "FLASHUNIT", "flashunit"));
                        c.put("IMAGEFORMATS", new CategoryValue(getImageFormat(chars), "IMAGEFORMATS", "imageformats"));
                        c.put("ORIENTATION", new CategoryValue(getOrientation(chars), "ORIENTATION", "orientation"));
                        c.put("FOCALLENGTH", new CategoryValue(getFocalLength(chars), "FOCALLENGTH", "focallength"));
                        c.put("SENSORSIZE", new CategoryValue(getSensorSize(chars), "SENSORSIZE", "sensorsize"));
                    }
                    if (!"".equals(cameraVendors)) {
                        c.put("MANUFACTURER", new CategoryValue(getManufacturer(index), "MANUFACTURER", "manufacturer"));
                    }
                    c.put("RESOLUTIONVIDEO", new CategoryValue(getVideoResolution(index), "RESOLUTIONVIDEO", "resolutionvideo"));
                    c.put("SUPPORTS", new CategoryValue("N/A", "SUPPORTS", "supports"));
                    c.put("MODEL", new CategoryValue(getModel(index), "MODEL", "model"));
                    this.add(c);
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA, ex.getMessage()));
        }
    }

    public int getCountCamera(Context xCtx) {
	    int value = 0;
	    try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CameraManager manager = (CameraManager) xCtx.getApplicationContext().getSystemService(Context.CAMERA_SERVICE);
                try {
                    assert manager != null;
                    value = manager.getCameraIdList().length;
                } catch (CameraAccessException e) {
                    InventoryLog.e(e.getMessage());
                } catch (NullPointerException e) {
                    InventoryLog.e(e.getMessage());
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_COUNT, ex.getMessage()));
        }
        return value;
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
            } catch (Exception ex) {
                InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_CHARACTERISTICS, ex.getMessage()));
            }
        }
        return null;
    }

    /**
     * Get resolution from the camera
     * @param characteristics
     * @return String resolution camera
     */
    public String getResolution(CameraCharacteristics characteristics) {
        String value = "N/A";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                int width = 0, height = 0;
                StreamConfigurationMap sizes = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (sizes != null) {
                    Size[] outputSizes = sizes.getOutputSizes(256);
                    if (outputSizes == null || outputSizes.length <= 0) {
                        Rect rect = characteristics.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE);
                        if (rect != null) {
                            width = rect.width();
                            height = rect.height();
                        }
                    } else {
                        Size size = outputSizes[outputSizes.length - 1];
                        width = size.getWidth();
                        height = size.getHeight();
                    }
                    value = width + "x" + height;
                } else {
                    return value;
                }
            } else {
                return value;
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_RESOLUTION, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get direction the camera faces relative to device screen
     * @param characteristics
     * @return String The camera device faces the same direction as the device's screen
     */
    public String getFacingState(CameraCharacteristics characteristics) {
        String value = "N/A";
        try {
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
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_FACING_STATE, ex.getMessage()));
        }
        return value;
    }

    /**
     * Whether this camera device has a flash unit.
     * @param characteristics
     * @return String 0 no available, 1 is available
     */
    public String getFlashUnit(CameraCharacteristics characteristics) {
        String value = "N/A";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Boolean bool = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                return bool != null ? bool ? "1" : "0" : "N/A";
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_FLASH_UNIT, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get image format camera
     * @param characteristics
     * @return String image format camera
     */
    public ArrayList<String> getImageFormat(CameraCharacteristics characteristics) {
        ArrayList<String> types = new ArrayList<>();
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                StreamConfigurationMap configurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                if (configurationMap != null) {
                    int[] outputFormats = configurationMap.getOutputFormats();
                    if (outputFormats != null) {
                        for (int value : outputFormats) {
                            String type = typeFormat(value);
                            types.add(type.replaceAll("[<>]", ""));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_IMAGE_FORMAT, ex.getMessage()));
        }
        return types;
    }

    private String typeFormat(int i) {
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
                return "";
        }
    }

    /**
     * Get orientation camera
     * @param characteristics
     * @return String orientation camera
     */
    public String getOrientation(CameraCharacteristics characteristics) {
        String value = "N/A";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                return String.valueOf(characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION));
            } else {
                return value;
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_ORIENTATION, ex.getMessage()));
        }
        return value;
    }


    /**
     * Get video resolution camera
     * @param index number of camera
     * @return String video resolution camera
     */
    public String getVideoResolution(int index) {
        String value = "N/A";
        try {
            Camera open = Camera.open(index);
            Camera.Parameters parameters = open.getParameters();
            List<Camera.Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
            if (supportedVideoSizes != null) {
                Camera.Size infoSize = supportedVideoSizes.get(supportedVideoSizes.size() - 1);
                int width = infoSize.width;
                int height = infoSize.height;
                value = width + "x" + height;
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_VIDEO_RESOLUTION, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get focal length camera
     * @param characteristics
     * @return String focal length camera
     */
    public String getFocalLength(CameraCharacteristics characteristics) {
        String value = "N/A";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                float[] fArr = characteristics.get(CameraCharacteristics.LENS_INFO_AVAILABLE_FOCAL_LENGTHS);
                if (fArr == null) {
                    return null;
                }
                StringBuilder str = new StringBuilder();
                for (float f : fArr) {
                    str.append(f);
                }
                return str.toString().trim();
            } else {
                return value;
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_FOCAL_LENGTH, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get sensor size camera
     * @param characteristics
     * @return String sensor size camera
     */
    public String getSensorSize(CameraCharacteristics characteristics) {
        String value = "N/A";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                SizeF sizeF = characteristics.get(CameraCharacteristics.SENSOR_INFO_PHYSICAL_SIZE);
                if (sizeF != null) {
                    double width = (double) sizeF.getWidth();
                    double height = (double) sizeF.getHeight();
                    if (width > 0.0d && height > 0.0d) {
                        return Math.round(width * 100) / 100.0d + "x" + Math.round(height * 100) / 100.0d;
                    }
                }
                return value;
            } else {
                return value;
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_SENSOR_SIZE, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get manufacturer camera
     * @param index number of the camera
     * @return String manufacturer camera
     */
    public String getManufacturer(int index) {
        String value = "N/A";
        try {
            String infoModel = Utils.getCatInfoMultiple("/proc/hw_info/camera_info");
            if ("".equals(infoModel)) {
                infoModel = Utils.getCatInfoMultiple("/proc/driver/camera_info");
            }
            if (!"".equals(infoModel) && infoModel.contains(";")) {
                String infoName = infoModel.split(";", 2)[index];
                if (infoName.contains(":")) {
                    String infoValue = infoName.split(":", 2)[1].trim();
                    if (!"".equals(infoValue)) {
                        JSONArray jr = new JSONArray(cameraVendors);
                        for (int i = 0; i < jr.length(); i++) {
                            JSONObject c = jr.getJSONObject(i);
                            String id = c.getString("id");
                            if (infoValue.startsWith(id)) {
                                value = c.getString("name");
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_MANUFACTURER, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get model camera
     * @param index number of the camera
     * @return String model camera
     */
    public String getModel(int index) {
        String value = "N/A";
        try {
            String infoModel = Utils.getCatInfoMultiple("/proc/driver/camera_info");
            if ("".equals(infoModel)) {
                infoModel = Utils.getCatInfoMultiple("/proc/hw_info/camera_info");
            }
            if (!"".equals(infoModel)) {
                infoModel = infoModel.split(";", 2)[index];
                if (infoModel.contains(":"))
                    value = infoModel.split(":", 2)[1];
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_MODEL, ex.getMessage()));
        }
        return value;
    }

    /**
     * Get support camera
     * @return String support camera
     */
    public ArrayList<String> getSupportValue() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator it = getCatInfoCamera("/system/lib/libcameracustom.so", "SENSOR_DRVNAME_", 100).iterator();
        while (it.hasNext()) {
            arrayList.add(((String) it.next()).toLowerCase(Locale.US));
        }
        return arrayList;
    }

    private ArrayList<String> getCatInfoCamera(String str, String str2, int i) {
        InputStream bufferedInputStream;
        Throwable th;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(str);
            try {
                byte[] bytes = str2.getBytes();
                bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                try {
                    byte[] bArr = new byte[1];
                    int i2 = 0;
                    int i3 = 0;
                    int i5 = 0;
                    while (true) {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        int i6;
                        i5 += read;
                        byte b = bArr[0];
                        if (i3 == str2.length()) {
                            arrayList.add(((char) b) + getValueString(getListBytes(bufferedInputStream, (byte) 0)));
                            i3 = i5;
                            i2 = 0;
                        } else {
                            i6 = i2;
                            i2 = i3;
                            i3 = i6;
                        }
                        i2 = b == bytes[i2] ? i2 + 1 : 0;
                        if (i3 > 0 && i5 - i3 > i) {
                            break;
                        }
                        i6 = i3;
                        i3 = i2;
                        i2 = i6;
                    }
                    bufferedInputStream.close();
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream.close();
                    throw th;
                }
            } catch (Throwable th3) {
                InventoryLog.e(th3.getMessage());
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_BUFFERED, ex.getMessage()));
        }
        return arrayList;
    }

    private String getValueString(List<Byte> list) {
        try {
            if (list.size() <= 0) {
                return "";
            }
            byte[] bArr = new byte[list.size()];
            int i = 0;
            for (Byte byteValue : list) {
                bArr[i] = byteValue;
                i++;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                return new String(bArr, StandardCharsets.UTF_8);
            } else {
                return new String(bArr, Charset.forName("UTF-8"));
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_VALUE_STRING, ex.getMessage()));
        }
        return "";
    }

    private List<Byte> getListBytes(InputStream inputStream, byte b) {
        byte[] bArr = new byte[1];
        List<Byte> arrayList = new ArrayList<>();
        try {
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byte b2 = bArr[0];
                    if (b2 == b) {
                        break;
                    }
                    arrayList.add(b2);
                } catch (IOException e) {
                    InventoryLog.e(e.getMessage());
                }
            }
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.CAMERA_LIST_BYTES, ex.getMessage()));
        }
        return arrayList;
    }

}
