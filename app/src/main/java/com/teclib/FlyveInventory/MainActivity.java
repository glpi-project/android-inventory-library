package com.teclib.FlyveInventory;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flyvemdm.inventory.InventoryTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private InventoryTask task;
    private ProgressBar pb;
    private static final String ACTION_USB_PERMISSION = "com.teclib.FlyveInventory.USB_PERMISSION";
    PendingIntent mPermissionIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instance of FlyveInventory Library
        task = new InventoryTask(MainActivity.this, "Agent");

        // A Progressbar to wait for a Thread
        pb = (ProgressBar) findViewById(R.id.progressBar);

        // Request all the permissions that the library need
        int permissionAll = 1;
        String[] permissions = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, permissionAll);
        }

        /*
         * this block required if you need to communicate to USB devices it's
         * take permission to device
         * if you want than you can set this to which device you want to communicate
         */
        // ------------------------------------------------------------------
        mPermissionIntent = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        // create XML file
        Button btnXML = (Button) findViewById(R.id.btnXML);
        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);

                // Get XML format and Write it on a file
                task.getXML(new InventoryTask.OnTaskCompleted() {
                    @Override
                    public void onTaskSuccess(String data) {
                        pb.setVisibility(View.GONE);

                        try {
                            generateFileOnSD(MainActivity.this, "Flyve-Inventory-XML.xml", data);
                        } catch (Exception ex) {
                            Toast.makeText( MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG ).show();
                        }

                        Log.d("XML Inventory", data);
                    }

                    @Override
                    public void onTaskError(Throwable error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_LONG ).show();
                        Log.d("Error XML", error.getMessage());
                    }
                });
            }
        });

        // create JSON file
        Button btnJSON = (Button) findViewById(R.id.btnJSON);
        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);

                // Get JSON format and Write it on a file
                task.getJSON(new InventoryTask.OnTaskCompleted() {
                    @Override
                    public void onTaskSuccess(String data) {
                        pb.setVisibility(View.GONE);

                        try {
                            generateFileOnSD(MainActivity.this, "Flyve-Inventory-JSON.json", data);
                        } catch (Exception ex) {
                            Toast.makeText( MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG ).show();
                        }

                        Log.d("JSON Inventory", data);
                    }

                    @Override
                    public void onTaskError(Throwable error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText( MainActivity.this, error.getMessage(), Toast.LENGTH_LONG ).show();
                        Log.d("Error JSON", error.getMessage());
                    }
                });
            }
        });
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            // call method to set up device communication
                        }
                    } else {
                        Log.d("ERROR", "permission denied for device " + device);
                    }
                }
            }
        }
    };

    /**
     * This function request the permission needed on Android 6.0 and above
     * @param context The context of the app
     * @param permissions The list of permissions needed
     * @return true or false
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Simple function to generate a file on SD card
     * @param context The context of the app
     * @param sFileName Name of the File
     * @param sBody What do you want to be in the file
     */
    public void generateFileOnSD(Context context, String sFileName, String sBody) throws IOException {
        FileWriter writer = null;
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Flyve");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText( MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG ).show();
            Log.d("Error FILE", ex.getMessage());
        }
        finally {
            if(writer != null) {
                writer.close();
            }
        }
    }
}
