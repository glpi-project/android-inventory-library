package com.teclib.FlyveInventory;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        task = new InventoryTask(MainActivity.this, "Agent");
        pb = (ProgressBar) findViewById(R.id.progressBar);

        Button btnXML = (Button) findViewById(R.id.btnXML);
        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);

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
                    public void onTaskError(String error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText( MainActivity.this, error, Toast.LENGTH_LONG ).show();
                        Log.d("Error XML", error);
                    }
                });
            }
        });

        Button btnJSON = (Button) findViewById(R.id.btnJSON);
        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pb.setVisibility(View.VISIBLE);

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
                    public void onTaskError(String error) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText( MainActivity.this, error, Toast.LENGTH_LONG ).show();
                        Log.d("Error JSON", error);
                    }
                });
            }
        });
    }

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
        } catch (IOException e) {
            Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
            Log.d("Error FILE", e.getMessage());
        }
        finally {
            if(writer != null) {
                writer.close();
            }
        }
    }
}
