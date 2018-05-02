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

package org.flyve.inventory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import org.flyve.inventory.categories.Categories;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * This class generate the XML file
 */
public class InventoryTask {

    private static Handler uiHandler;

    private Boolean running = false;

    static {
        uiHandler = new Handler(Looper.getMainLooper());
    }

    private static void runOnUI(Runnable runnable) {
        uiHandler.post(runnable);
    }

    private Context ctx = null;
    private String appVersion = "";
    private String fileNameXML = "Inventory.xml";
    private String fileNameJSON = "Inventory.json";
    private Boolean storeResult = false;
    private String TAG = "";
    private boolean privateData = false;

    public Boolean isRunning() {
        return running;
    }

    /**
     * This constructor return a Success XML or Error on asynchronous way
     * @param context The context to be use
     * @param appVersion The name of the agent
     */
    public InventoryTask(Context context, String appVersion) {
        this.appVersion = appVersion;
        ctx = context;

        try {
            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                    .build();
            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }

    /**
     * This constructor return a Success XML or Error on asynchronous way
     * @param context The context to be use
     * @param appVersion The name of the agent
     * @param storeResult Indicate is the result will be stored on file
     */
    public InventoryTask(Context context, String appVersion, Boolean storeResult) {
        this.appVersion = appVersion;
        this.storeResult = storeResult;
        ctx = context;
    }

    /**
     * This function load all the categories class dynamically
     * @return ArrayList<Categories>
     */
    @SuppressWarnings("unchecked")
    private ArrayList<Categories> loadCategoriesClass() throws FlyveException {

        ArrayList<Categories> mContent = new ArrayList<Categories>();

        String[] categories = {
//                "PhoneStatus",
                "Hardware",
                "User",
                "Storage",
                "OperatingSystem",
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
                Class cCat = Class.forName(String.format("org.flyve.inventory.categories.%s", c));
                catClass = (Class<Categories>)cCat;

            }
            catch (Exception ex) {
                FILog.e( ex.getCause().toString() );
                throw new FlyveException(ex.getMessage(), ex.getCause());
            }

            // Instance the class and checking errors
            if(catClass!=null) {
                try {
                    Constructor<Categories> co = catClass.getConstructor(Context.class);
                    mContent.add(co.newInstance(ctx));
                } catch ( Exception ex ) {
                    FILog.e( ex.getCause().toString() );
                    throw new FlyveException(ex.getMessage(), ex.getCause());
                }
            }
        }
        return mContent;
    }

    public void setTag(String tag) {
        TAG = tag;
    }

    public String getTag() {
        return TAG;
    }

    public void setPrivateData(boolean enable) {
        privateData = enable;
    }

    public boolean getPrivateData() {
        return privateData;
    }

    /**
     * Return a XML String or Error OnTaskCompleted interface
     * @param listener the interface OnTaskCompleted
     */
    public void getXML(final OnTaskCompleted listener) {

        running = true;
        Thread t = new Thread(new Runnable()
        {
            public void run() {

                try {
                    ArrayList<Categories> mContent = loadCategoriesClass();
                    final String xml = Utils.createXML(ctx, mContent, InventoryTask.this.appVersion, getPrivateData(), getTag());

                    if(storeResult) {
                        Utils.storeFile(xml, fileNameXML);
                    }

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            running = false;
                            listener.onTaskSuccess( xml );
                        }
                    });
                } catch (final Exception ex) {

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            running = false;
                            listener.onTaskError( ex.getCause() );
                        }
                    });

                }
            }
        });
        t.start();
    }

    /**
     * Return a JSON String or Error OnTaskCompleted interface
     * @param listener the interface OnTaskCompleted
     */
    public void getJSON(final OnTaskCompleted listener) {
        running = true;
        Thread t = new Thread(new Runnable()
        {
            public void run() {

                try {
                    ArrayList<Categories> mContent = loadCategoriesClass();
                    final String json = Utils.createJSON(ctx, mContent, InventoryTask.this.appVersion, getPrivateData(), getTag());

                    if(storeResult) {
                        Utils.storeFile(json, fileNameJSON);
                    }

                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            running = false;
                            listener.onTaskSuccess( json );
                        }
                    });

                } catch (final Exception ex) {
                    InventoryTask.runOnUI(new Runnable() {
                        public void run() {
                            running = false;
                            listener.onTaskError( ex.getCause() );
                        }
                    });
                }
            }
        });
        t.start();
    }

    /**
     * Return a JSON String synchronously
     */
    public String getJSONSync() {
        try {
            ArrayList<Categories> mContent = loadCategoriesClass();
            String json = Utils.createJSON(ctx, mContent, InventoryTask.this.appVersion, getPrivateData(), getTag());

            if(storeResult) {
                Utils.storeFile(json, fileNameJSON);
            }

            return json;

        } catch (final Exception ex) {
            Log.e("Library Exception", ex.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Return a XML String synchronously
     */
    public String getXMLSyn() {
        try {
            ArrayList<Categories> mContent = loadCategoriesClass();
            String xml = Utils.createXML(ctx, mContent, InventoryTask.this.appVersion, getPrivateData(), getTag());

            if(storeResult) {
                Utils.storeFile(xml, fileNameXML);
            }

            return xml;

        } catch (final Exception ex) {
            Log.e("Library Exception", ex.getLocalizedMessage());
            return null;
        }
    }

    public void shareInventory(int type){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        if(type==1) {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path + "/Inventory.json")));
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(path + "/Inventory.xml")));
        }
        this.ctx.startActivity(Intent.createChooser(intent, "Share your inventory"));
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
        void onTaskError(Throwable error);

    }
}
