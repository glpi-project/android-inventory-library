package org.flyve.example_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import org.flyve.inventory.InventoryTask

class MainActivity : AppCompatActivity() {

    val LOG = "inventory.example"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InventoryTask.showFILog = true
//        val categories: Array<String> = arrayOf("Hardware", "OperatingSystem")
        val inventoryTask = InventoryTask(this@MainActivity, "example-app-kotlin", false)
        inventoryTask.tag = "Android Lenovo"
        inventoryTask.getXML( object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable) {
                Log.e(LOG, error.message)
            }
        } )
        inventoryTask.getJSON( object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String?) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable?) {
                Log.e(LOG, error?.message)
            }

        })

    }
}
