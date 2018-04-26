package org.flyve.example_java;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.flyve.inventory.InventoryTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "inventory.example";
    private InventoryTask inventoryTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                },
                1);

        Button btnRun = findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryTask = new InventoryTask(MainActivity.this, "example-app-java", true);
                inventoryTask.getXML(new InventoryTask.OnTaskCompleted() {
                    @Override
                    public void onTaskSuccess(String data) {
                        Log.d(TAG, data);
                        share(MainActivity.this, data, 0);
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

    public void share(Context context, String message, int type){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if(type==1) {
            intent.putExtra(Intent.EXTRA_STREAM, inventoryTask.getCacheFilePath("json"));
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, inventoryTask.getCacheFilePath("xml"));
        }

        context.startActivity(Intent.createChooser(intent, "Share your inventory"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }

}
