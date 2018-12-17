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
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Xml;

import org.flyve.inventory.categories.Categories;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Utils {

    private static int rating = -1;

    private Utils() {

    }

    /**
     * Detects if it is an emulator or a real dvice
     * @return true for emulator, false for real device
     */
    public static boolean isEmulator() {
        int newRating = 0;
        if(rating < 0) {
            if (Build.PRODUCT.contains("sdk") ||
                    Build.PRODUCT.contains("Andy") ||
                    Build.PRODUCT.contains("ttVM_Hdragon") ||
                    Build.PRODUCT.contains("google_sdk") ||
                    Build.PRODUCT.contains("Droid4X") ||
                    Build.PRODUCT.contains("nox") ||
                    Build.PRODUCT.contains("sdk_x86") ||
                    Build.PRODUCT.contains("sdk_google") ||
                    Build.PRODUCT.contains("vbox86p")) {
                newRating++;
            }

            if (Build.MANUFACTURER.equals("unknown") ||
                    Build.MANUFACTURER.equals("Genymotion") ||
                    Build.MANUFACTURER.contains("Andy") ||
                    Build.MANUFACTURER.contains("MIT") ||
                    Build.MANUFACTURER.contains("nox") ||
                    Build.MANUFACTURER.contains("TiantianVM")){
                newRating++;
            }

            if (Build.BRAND.equals("generic") ||
                    Build.BRAND.equals("generic_x86") ||
                    Build.BRAND.equals("TTVM") ||
                    Build.BRAND.contains("Andy")) {
                newRating++;
            }

            if (Build.DEVICE.contains("generic") ||
                    Build.DEVICE.contains("generic_x86") ||
                    Build.DEVICE.contains("Andy") ||
                    Build.DEVICE.contains("ttVM_Hdragon") ||
                    Build.DEVICE.contains("Droid4X") ||
                    Build.DEVICE.contains("nox") ||
                    Build.DEVICE.contains("generic_x86_64") ||
                    Build.DEVICE.contains("vbox86p")) {
                newRating++;
            }

            if (Build.MODEL.equals("sdk") ||
                    Build.MODEL.equals("google_sdk") ||
                    Build.MODEL.contains("Droid4X") ||
                    Build.MODEL.contains("TiantianVM") ||
                    Build.MODEL.contains("Andy") ||
                    Build.MODEL.equals("Android SDK built for x86_64") ||
                    Build.MODEL.equals("Android SDK built for x86")) {
                newRating++;
            }

            if (Build.HARDWARE.equals("goldfish") ||
                    Build.HARDWARE.equals("vbox86") ||
                    Build.HARDWARE.contains("nox") ||
                    Build.HARDWARE.contains("ttVM_x86")) {
                newRating++;
            }

            if (Build.FINGERPRINT.contains("generic/sdk/generic") ||
                    Build.FINGERPRINT.contains("generic_x86/sdk_x86/generic_x86") ||
                    Build.FINGERPRINT.contains("Andy") ||
                    Build.FINGERPRINT.contains("ttVM_Hdragon") ||
                    Build.FINGERPRINT.contains("generic_x86_64") ||
                    Build.FINGERPRINT.contains("generic/google_sdk/generic") ||
                    Build.FINGERPRINT.contains("vbox86p") ||
                    Build.FINGERPRINT.contains("generic/vbox86p/vbox86p")) {
                newRating++;
            }

            rating = newRating;
        }
        return rating > 3;
    }

    /**
     * Create a JSON String with al the Categories available
     * @param categories ArrayList with the categories
     * @param appVersion Name of the agent
     * @return String with JSON
     * @throws FlyveException Exception
     */

    protected static String createJSON(Context context, ArrayList<Categories> categories, String appVersion,
                                       boolean isPrivate, String tag) throws FlyveException {
        try {

            if(!tag.equals("")) {
                JSONObject accountInfo = new JSONObject();
                accountInfo.put("keyName", "TAG");
                accountInfo.put("keyValue", tag);
            }

            JSONObject jsonAccessLog = new JSONObject();
            jsonAccessLog.put("logDate", DateFormat.format("yyyy-MM-dd H:mm:ss", new Date()).toString());
            jsonAccessLog.put("userId", "N/A");

            jsonAccessLog.put("keyname", "TAG");
            jsonAccessLog.put("keyvalue", "N/A");

            JSONObject content = new JSONObject();
            content.put("accessLog", jsonAccessLog);
            content.put("accountInfo", jsonAccessLog);
            content.put("versionClient", appVersion);
            content.put("accountinfo", tag);

            for (Categories cat : categories) {
                if(isPrivate) {
                    cat.toJSONWithoutPrivateData(content);
                } else {
                    cat.toJSON(content);
                }
            }

            JSONObject jsonQuery = new JSONObject();
            jsonQuery.put("query", "INVENTORY");
            jsonQuery.put("versionClient", appVersion);
            jsonQuery.put("deviceId", getDeviceId());
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                jsonQuery.put("IMEI", telephonyManager.getDeviceId());
            }
            jsonQuery.put("content", content);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("request", jsonQuery);

            return jsonRequest.toString();

        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.UTILS_CREATE_JSON), ex.getMessage()));
            throw new FlyveException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Create a XML String with al the Categories available
     * @param categories ArrayList with the categories
     * @param appVersion Name of the agent
     * @return String with XML
     * @throws FlyveException Exception
     */
    protected static String createXML(Context context, ArrayList<Categories> categories, String appVersion,
                                      boolean isPrivate, String tag) throws FlyveException {
        if (categories != null) {
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            try {
                serializer.setOutput(writer);
                serializer.setFeature(
                                "http://xmlpull.org/v1/doc/features.html#indent-output",
                                true);
                // indentation as 3 spaces

                serializer.startDocument("utf-8", true);
                // Start REQUEST
                serializer.startTag(null, "REQUEST");
                serializer.startTag(null, "QUERY");
                serializer.text("INVENTORY");
                serializer.endTag(null, "QUERY");

                serializer.startTag(null, "DEVICEID");
                serializer.text(getDeviceId());
                serializer.endTag(null, "DEVICEID");

                // Start CONTENT
                serializer.startTag(null, "CONTENT");

                serializer.startTag(null, "VERSIONCLIENT");
                serializer.text(appVersion);
                serializer.endTag(null, "VERSIONCLIENT");

                // Start ACCOUNTINFO
                serializer.startTag(null, "ACCOUNTINFO");

                serializer.startTag(null, "KEYNAME");
                serializer.text("TAG");
                serializer.endTag(null, "KEYNAME");

                serializer.startTag(null, "KEYVALUE");
                serializer.text(tag);
                serializer.endTag(null, "KEYVALUE");

                serializer.endTag(null, "ACCOUNTINFO");

                // Start ACCESSLOG
                serializer.startTag(null, "ACCESSLOG");

                serializer.startTag(null, "LOGDATE");
                serializer.text(DateFormat.format("yyyy-MM-dd H:mm:ss", new Date()).toString());
                serializer.endTag(null, "LOGDATE");

                serializer.startTag(null, "USERID");
                serializer.text("N/A");
                serializer.endTag(null, "USERID");

                serializer.endTag(null, "ACCESSLOG");
                // End ACCESSLOG

                if(!tag.equals("")) {
                    serializer.startTag(null, "ACCOUNTINFO");
                    serializer.startTag(null, "KEYNAME");
                    serializer.text("TAG");
                    serializer.endTag(null, "KEYNAME");
                    serializer.startTag(null, "KEYVALUE");
                    serializer.text(tag);
                    serializer.endTag(null, "KEYVALUE");
                    serializer.endTag(null, "ACCOUNTINFO");
                }

                for (Categories cat : categories) {
                    if(isPrivate) {
                        cat.toXMLWithoutPrivateData(serializer);
                    } else {
                        cat.toXML(serializer);
                    }
                }

                serializer.endTag(null, "CONTENT");
                // End CONTENT
                serializer.endTag(null, "REQUEST");
                // End REQUEST

                serializer.endDocument();

                // Return XML String
                return writer.toString();

            } catch (Exception ex) {
                FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.UTILS_CREATE_XML), ex.getMessage()));
                throw new FlyveException(ex.getMessage(), ex.getCause());
            }
        }

        return "";
    }

    private static String getDeviceId() {
        String currentTimeStamp = getCurrentTimeStamp("yyyy-MM-dd-HH-mm-ss");
        String hostName = getSystemProperty("net.hostname");
        return hostName.trim() + "-" + currentTimeStamp.trim();
    }

    public static Map<String, String> getCatMapInfo(String path) throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();
        Scanner s = new Scanner(new File(path));
        while (s.hasNextLine()) {
            String[] values = s.nextLine().split(": ");
            if (values.length > 1) {
                map.put(values[0].trim(), values[1].trim());
            }
        }
        return map;
    }

    public static String getValueMapInfo(Map<String, String> mapInfo, String name) {
        for (String s : mapInfo.keySet()) {
            if (s.equalsIgnoreCase(name)) {
                return mapInfo.get(s);
            }
        }
        return "";
    }

    public static String getCatInfoMultiple(String path) {
        StringBuilder concatInfo = new StringBuilder();
        try {
            Scanner s = new Scanner(new File(path));
            while (s.hasNextLine()) {
                concatInfo.append(s.nextLine());
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.UTILS_CAT_INFO_MULTIPLE), ex.getMessage()));
        }
        return concatInfo.toString();
    }

    public static String getCatInfo(String path) {
        try {
            Scanner s = new Scanner(new File(path));
            return s.next();
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.UTILS_CAT_INFO), ex.getMessage()));
        }
        return "";
    }

    public static BufferedReader getBufferedSequentialCatInfo(String[] values) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(values[0]);
        DataOutputStream dos = new DataOutputStream(p.getOutputStream());
        for (int i = 1; i < values.length; i++)
            dos.writeBytes(values[i]);
        dos.flush();
        dos.close();
        p.waitFor();
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    public static String getSystemProperty(String type) {
        ArrayList<HashMap<String, String>> arr = getDeviceProperties();
        for (int i = 0; i < arr.size(); i++) {
            HashMap<String, String> map = arr.get(i);
            if (map.get(type) != null)
                return map.get(type);
        }
        return "";
    }

    public static ArrayList<HashMap<String, String>> getDeviceProperties() {
        try {
            // Run the command
            Process process = Runtime.getRuntime().exec("getprop");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            // Grab the results
            ArrayList<HashMap<String, String>> arr = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] value = line.split(":");
                HashMap<String, String> map = new HashMap<>();
                if (value.length >= 2) {
                    map.put(removeBracket(value[0]), removeBracket(value[1]));
                    arr.add(map);
                }
            }

            return arr;
        } catch (IOException ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.UTILS_DEVICE_PROPERTIES), ex.getMessage()));
        }

        return new ArrayList<>();
    }

    private static String removeBracket(String str) {
        return str.replaceAll("\\[", "").replaceAll("]", "");
    }

    /** Convert timestamp in a formatted date pattern
     * @param time timestamp
     * @param pattern string value
     * @return converted time
     */
    public static String convertTime(long time, String pattern) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    /** Get current date
     * @param template format date
     * @return String of the current date
     */
    public static String getCurrentTimeStamp(String template) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(template, Locale.getDefault());
        Date now = Calendar.getInstance().getTime();
        return sdfDate.format(now);
    }

    public static String loadJSONFromAsset(Context context, String name) {
        String json;
        try {
            InputStream is = context.getAssets().open(name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.UTILS_LOAD_JSON_ASSET), ex.getMessage()));
            return null;
        }
        return json;
    }

    /* Checks if external storage is available for read and write */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Logs the message in a directory
     * @param message the message
     * @param filename name of the file
     */
    public static void storeFile(String message, String filename) {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File dir = new File(path);

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            if(!dir.exists()) {
                if(dir.mkdirs()) {
                    FlyveLog.d("create path");
                } else {
                    FlyveLog.e("cannot create path");
                    return;
                }
            }

            File logFile = new File(path + "/" + filename);

            if (!logFile.exists())  {
                try  {
                    if(logFile.createNewFile()) {
                        FlyveLog.d("File created");
                    } else {
                        FlyveLog.d("Cannot create file");
                        return;
                    }
                } catch (IOException ex) {
                    FlyveLog.e(ex.getMessage());
                }
            }

            FileWriter fw = null;

            try {
                //BufferedWriter for performance, true to set append to file flag
                fw = new FileWriter(logFile, false);
                BufferedWriter buf = new BufferedWriter(fw);

                buf.write(message);
                buf.newLine();
                buf.flush();
                buf.close();
                fw.close();
                FlyveLog.d("Inventory stored");
            }
            catch (IOException ex) {
                FlyveLog.e(ex.getMessage());
            }
            finally {
                if(fw!=null) {
                    try {
                        fw.close();
                    } catch(Exception ex) {
                        FlyveLog.e(ex.getMessage());
                    }
                }
            }
        } else {
            FlyveLog.d("External Storage is not available");
        }
    }

}
