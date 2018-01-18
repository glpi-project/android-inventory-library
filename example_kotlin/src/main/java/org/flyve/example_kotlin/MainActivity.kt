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

        val inventoryTask = InventoryTask(this@MainActivity, "example-app-kotlin")
        inventoryTask.getJSON( object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable) {
                Log.e(LOG, error.message)
            }
        } )

    }
}
