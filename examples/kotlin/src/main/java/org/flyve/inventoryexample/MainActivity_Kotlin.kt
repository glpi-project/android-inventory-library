package org.flyve.inventoryexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import org.flyve.inventory.InventoryTask

class MainActivity_Kotlin : AppCompatActivity() {

    private val LOG = "FlyveInventoryExample"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inventoryTask = InventoryTask(this@MainActivity_Kotlin, "inventoryExample")

        inventoryTask.getJSON(object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable) {
                Log.e(LOG, error.message)
            }
        })

        inventoryTask.getXML(object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable) {
                Log.e(LOG, error.message)
            }
        })
    }
}
