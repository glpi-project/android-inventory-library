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
import android.os.AsyncTask;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Xml;

import com.flyvemdm.inventory.categories.Categories;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class generate the XML file
 */
public class InventoryTask extends AsyncTask<String, Void, String> {

    private ArrayList<Categories> mContent = null;
    private Date mStart = null, mEnd = null;
    private Context ctx = null;
    private static final int OK = 0;
    private static final int NOK = 1;
    private String FusionVersion = "";
    private int progress = 0;
    private OnTaskCompleted listener;

    /**
     * This constructor return a Success XML or Error on asynchronous way
     * @param context The context to be use
     * @param appVersion The name of the agent
     * @param listener Here you will get the data or error information
     */
    public InventoryTask(Context context, String appVersion, OnTaskCompleted listener) {
        this.FusionVersion = appVersion;
        this.listener = listener;
        ctx = context;
        FILog.v("FusionInventoryApp = ");
    }

    /**
     * This function create and return the XML
     * @return String with XML
     * @throws RuntimeException
     */
    private String createXML() throws RuntimeException {
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
                serializer.text(FusionVersion);
                serializer.endTag(null, "VERSIONCLIENT");

                serializer.startTag(null, "DEVICEID");
                serializer.text(Build.SERIAL);
                serializer.endTag(null, "DEVICEID");

                // Start CONTENT
                serializer.startTag(null, "CONTENT");

                // Start ACCESSLOG
                serializer.startTag(null, "ACCESSLOG");

                serializer.startTag(null, "LOGDATE");
                serializer.text(DateFormat.format("yyyy-MM-dd H:mm:ss", mStart).toString());
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
                throw new RuntimeException(e);
            }
        }
        return "";
    }

    /**
     * This step is used to perform background computation that can take a long time
     * @param params String params
     * @return String
     */
    @Override
    protected String doInBackground(String... params) {
        mStart = new Date();

        mContent = new ArrayList<Categories>();

        String [] categories = {
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
                "Softwares",
                "Usb",
                "Battery"
        };

        Class<Categories> cat_class;

        for(String c : categories) {
            cat_class = null;
            FILog.v(String.format("new INVENTORY of %s", c));

            // Loading the class with name of the ArrayList
            try {
                cat_class = (Class <Categories>) Class.forName(String.format("com.flyvemdm.inventory.categories.%s",c));
            } catch (ClassNotFoundException e) {
                FILog.e(e.getMessage());
                return e.getMessage();
            }

            // Instance the class and checking errors
            if(cat_class!=null) {
                try {
                    Constructor<Categories> co = cat_class.getConstructor(Context.class);
                    mContent.add(co.newInstance(ctx));
                } catch (NoSuchMethodException e) {
                    FILog.e(e.getMessage());
                    return e.getMessage();
                } catch (SecurityException e) {
                    FILog.e(e.getMessage());
                    return e.getMessage();
                } catch (IllegalArgumentException e) {
                    FILog.e(e.getMessage());
                    return e.getMessage();
                } catch (InstantiationException e) {
                    FILog.e(e.getMessage());
                    return e.getMessage();
                } catch (InvocationTargetException e) {
                    FILog.e(e.getMessage());
                    return e.getMessage();
                } catch (IllegalAccessException e) {
                    FILog.e(e.getMessage());
                    return e.getMessage();
                }
            }
        }
        FILog.v("end of inventory");
        mEnd = new Date();
        return "true";
    }

    /**
     * invoked on the UI thread after the background computation finishes.
     * @param result The result of the background computation is passed to this step as a parameter
     */
    @Override
    protected void onPostExecute(String result) {
        if(result.equals("true")){

            try {
                this.listener.onTaskSuccess(createXML());
            } catch (Exception e) {
                this.listener.onTaskError(e.getMessage());
            }

        } else {
            this.listener.onTaskError( result );
        }
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
