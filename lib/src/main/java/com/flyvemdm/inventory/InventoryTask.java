/**
 * FILog
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

package com.flyvemdm.inventory;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.Xml;

import com.flyvemdm.inventory.categories.Categories;

import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class generate the XML file
 */
public class InventoryTask {

    private static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    private static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    private Context ctx = null;
    private String appVersion = "";

    /**
     * This constructor return a Success XML or Error on asynchronous way
     * @param context The context to be use
     * @param appVersion The name of the agent
     */
    public InventoryTask(Context context, String appVersion) {
        this.appVersion = appVersion;
        ctx = context;
    }

    @SuppressWarnings("unchecked")
    private ArrayList<Categories> loadCategoriesClass() {

        ArrayList<Categories> mContent = new ArrayList<Categories>();

        String[] categories = {
//                "PhoneStatus",
                "Hardware",
                "Bios",
                "Memory",
                "Inputs",
                "Sensors",
                "Drives",
                "Cpus",
                "Simcards",
                "Videos",
                "Cameras",
                "Networks",
//                "LocationProviders",
                "Envs",
                "Jvm",
                "Software",
                "Usb",
                "Battery"
        };

        Class<Categories> catClass = null;

        for(String c : categories) {
            FILog.v(String.format("new INVENTORY of %s", c));

            // Loading the class with name of the ArrayList
            try {
                Class cCat = Class.forName(String.format("com.flyvemdm.inventory.categories.%s", c));
                catClass = (Class<Categories>)cCat;

            }
            catch (NullPointerException ex) {
                FILog.e(ex.getMessage());
            }
            catch (ClassNotFoundException ex) {
                FILog.e(ex.getMessage());
                return new ArrayList<Categories>();
            }

            // Instance the class and checking errors
            if(catClass!=null) {
                try {
                    Constructor<Categories> co = catClass.getConstructor(Context.class);
                    mContent.add(co.newInstance(ctx));
                } catch ( Exception ex ) {
                    FILog.e( ex.getMessage() );
                    return new ArrayList<Categories>();
                }
            }
        }

        return mContent;
    }

    public void getXML(final OnTaskCompleted listener) {

        Thread t = new Thread(new Runnable()
        {
            public void run() {

                try {
                    ArrayList<Categories> mContent = loadCategoriesClass();
                    final String xml = createXML(mContent);

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            listener.onTaskSuccess( xml );
                        }
                    });
                } catch (final Exception ex) {

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            listener.onTaskError( ex.getMessage() );
                        }
                    });

                }
            }
        });
        t.start();
    }

    public void getJSON(final OnTaskCompleted listener) {

        Thread t = new Thread(new Runnable()
        {
            public void run() {

                try {
                    ArrayList<Categories> mContent = loadCategoriesClass();
                    final String json = createJSON(mContent);

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            listener.onTaskSuccess( json );
                        }
                    });
                } catch (final Exception ex) {

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            listener.onTaskError( ex.getMessage() );
                        }
                    });

                }
            }
        });
        t.start();
    }

    private String createJSON(ArrayList<Categories> mContent){

        try {

            JSONObject accountInfo = new JSONObject();
            accountInfo.put("KEYNAME", "TAG");
            accountInfo.put("KEYVALUE", "N/A");

            JSONObject jsonAccessLog = new JSONObject();
            jsonAccessLog.put("LOGDATE", DateFormat.format("yyyy-MM-dd H:mm:ss", new Date()).toString());
            jsonAccessLog.put("USERID", "N/A");

            JSONObject content = new JSONObject();
            content.put("ACCESSLOG", jsonAccessLog);
            content.put("ACCOUNTINFO", jsonAccessLog);
            for (Categories cat : mContent) {
                cat.toJSON(content);
            }

            JSONObject jsonQuery = new JSONObject();
            jsonQuery.put("QUERY", "INVENTORY");
            jsonQuery.put("VERSIONCLIENT", this.appVersion);
            jsonQuery.put("DEVICEID", Build.SERIAL);
            jsonQuery.put("CONTENT", content);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("REQUEST", jsonQuery);

            return jsonRequest.toString();

        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

        return "";
    }

    /**
     * This function create and return the XML
     * @return String with XML
     * @throws RuntimeException
     */
    private String createXML(ArrayList<Categories> mContent) {
        FILog.i("createXML: ");

        if (mContent != null) {

            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            try {
                serializer.setOutput(writer);
                serializer
                        .setFeature(
                                "http://xmlpull.org/v1/doc/features.html#indent-output",
                                true);
                // indentation as 3 spaces

                serializer.startDocument("utf-8", true);
                // Start REQUEST
                serializer.startTag(null, "REQUEST");
                serializer.startTag(null, "QUERY");
                serializer.text("INVENTORY");
                serializer.endTag(null, "QUERY");

                serializer.startTag(null, "VERSIONCLIENT");
                serializer.text(appVersion);
                serializer.endTag(null, "VERSIONCLIENT");

                serializer.startTag(null, "DEVICEID");
                serializer.text(Build.SERIAL);
                serializer.endTag(null, "DEVICEID");

                // Start CONTENT
                serializer.startTag(null, "CONTENT");

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

                serializer.startTag(null, "ACCOUNTINFO");
                serializer.startTag(null, "KEYNAME");
                serializer.text("TAG");
                serializer.endTag(null, "KEYNAME");
                serializer.startTag(null, "KEYVALUE");

                serializer.text("");
                serializer.endTag(null, "KEYVALUE");
                serializer.endTag(null, "ACCOUNTINFO");

                for (Categories cat : mContent) {

                    cat.toXML(serializer);
                }

                serializer.endTag(null, "CONTENT");
                // End CONTENT
                serializer.endTag(null, "REQUEST");
                // End REQUEST

                serializer.endDocument();

                // Return XML String
                return writer.toString();

            } catch (Exception e) {
                FILog.e(e.getMessage());
            }
        }

        return "";
    }

    /**
     * This is the interface of return data
     */
    public interface OnTaskCompleted {

        /**
         * if everything is Ok
         * @param data String
         */
        void onTaskSuccess(String data);

        /**
         * if something wrong
         * @param error String
         */
        void onTaskError(String error);

    }
}
