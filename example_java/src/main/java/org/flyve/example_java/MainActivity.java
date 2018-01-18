package org.flyve.example_java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.flyve.inventory.InventoryTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "inventory.example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRun = findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InventoryTask inventoryTask = new InventoryTask(MainActivity.this, "example-app-java");
                inventoryTask.getXML(new InventoryTask.OnTaskCompleted() {
                    @Override
                    public void onTaskSuccess(String data) {
                        Log.d(TAG, data);
                        Toast.makeText(MainActivity.this, "Inventory Success, check the log", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTaskError(Throwable error) {
                        Log.e(TAG, error.getMessage());
                        Toast.makeText(MainActivity.this, "Inventory fail, check the log", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
