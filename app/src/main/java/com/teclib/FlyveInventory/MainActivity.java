package com.teclib.FlyveInventory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.flyvemdm.inventory.InventoryTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InventoryTask task = new InventoryTask(MainActivity.this, "Agent");
        task.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("JSON Inventory", data);
            }

            @Override
            public void onTaskError(String error) {
                Log.d("Error JSON", error);
            }
        });

        task.getXML(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("XML Inventory", data);
            }

            @Override
            public void onTaskError(String error) {
                Log.d("Error XML", error);
            }
        });

    }
}
