package org.flyve.inventoryexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.flyve.inventory.InventoryTask;

public class MainActivity extends AppCompatActivity {

    private static String LOG = "FlyveInventoryExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InventoryTask inventoryTask = new InventoryTask(MainActivity.this, "inventoryExample");

        inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d(LOG,  data);
            }

            @Override
            public void onTaskError(Throwable error) {
                Log.e(LOG, error.getMessage());
            }
        });

        inventoryTask.getXML(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d(LOG,  data);
            }

            @Override
            public void onTaskError(Throwable error) {
                Log.e(LOG, error.getMessage());
            }
        });
    }
}
