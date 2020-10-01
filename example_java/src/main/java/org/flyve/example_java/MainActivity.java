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

package org.flyve.example_java;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.flyve.inventory.InventoryLog;
import org.flyve.inventory.InventoryTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "inventory.example";
    private InventoryTask inventoryTask;

    public void showDialogShare(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        builder.setTitle(R.string.dialog_share_title);

        final int[] type = new int[1];

        //list of items
        String[] items = context.getResources().getStringArray(R.array.export_list);
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type[0] = which;
                    }
                });

        String positiveText = context.getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        new InventoryTask(MainActivity.this, TAG, false).shareInventory(type[0]);
                    }
                });

        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                },
                1);

        final Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialogShare(getApplicationContext());
            }
        });

        btnShare.setVisibility(View.INVISIBLE);

        Button btnRun = findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            inventoryTask = new InventoryTask(MainActivity.this, "example-app-java", true);
            inventoryTask.getXML(new InventoryTask.OnTaskCompleted() {
                @Override
                public void onTaskSuccess(String data) {
                    InventoryLog.d(data);
                    Toast.makeText(MainActivity.this, "Inventory Success, check the log", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTaskError(Throwable error) {
                    InventoryLog.e(error.getMessage());
                    Toast.makeText(MainActivity.this, "Inventory fail, check the log", Toast.LENGTH_SHORT).show();
                }
            });
            inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
                @Override
                public void onTaskSuccess(String data) {
                    InventoryLog.d(data);
                    //show btn share
                    btnShare.setVisibility(View.VISIBLE);
                }

                @Override
                public void onTaskError(Throwable error) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }

    public static String base64encode(String text) {
        String rtext = "";
        if(text == null) { return ""; }
        try {
            byte[] data = text.getBytes("UTF-8");
            rtext = Base64.encodeToString(data, Base64.NO_WRAP | Base64.URL_SAFE);
            rtext = rtext.replaceAll("-", "+");
            rtext = rtext.replaceAll(" ", "+");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        }

        return rtext;
    }

    public static String getSyncWebData(final String url, final String data, final Map<String, String> header) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL dataURL = new URL(url);
            Log.d(TAG, "URL: " + url);
            HttpURLConnection conn = (HttpURLConnection)dataURL.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(500000);

//            for (Map.Entry<String, String> entry : header.entrySet()) {
//                conn.setRequestProperty(entry.getKey(), entry.getValue());
//            }

            // Send post request
            conn.setDoOutput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(data);
            os.flush();
            os.close();

            if(conn.getResponseCode() >= 400) {
                InputStream is = conn.getErrorStream();
                return inputStreamToString(is);
            }

            InputStream is = conn.getInputStream();
            return inputStreamToString(is);

        }
        catch (final Exception ex) {
            String error = ex.getMessage();
            Log.e(TAG, error);
            return error;
        }
    }

    private static String inputStreamToString(final InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }

}
