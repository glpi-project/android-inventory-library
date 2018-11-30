package org.flyve.example_kotlin

import android.Manifest
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.flyve.inventory.InventoryTask

class SecondActivity : AppCompatActivity() {

    val LOG = "inventory.example"
    private var inventoryTask: InventoryTask? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permission()
        generateTask()
    }

    private fun permission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA), 1)
    }

    private fun generateTask() {
        InventoryTask.showFILog = true
        /*val categories: Array<String> = arrayOf("Hardware", "OperatingSystem")*/
        val appVersion = "example-app-kotlin"
        inventoryTask = InventoryTask(this, appVersion, false)
        inventoryTask?.tag = "Android Lenovo"
        inventoryTask?.getXML(object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable) {
                Log.e(LOG, error.message)
            }
        })
        textHello.setOnClickListener {
            inventoryTask?.isEndIterCategories = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        inventoryTask?.isEndIterCategories = true
    }
}
