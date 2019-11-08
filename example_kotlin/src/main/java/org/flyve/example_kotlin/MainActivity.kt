/**
 *  LICENSE
 *
 *  This file is part of Flyve MDM Inventory Library for Android.
 * 
 *  Inventory Library for Android is a subproject of Flyve MDM.
 *  Flyve MDM is a mobile device management software.
 *
 *  Flyve MDM is free software: you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version.
 *
 *  Flyve MDM is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  ---------------------------------------------------------------------
 *  @copyright Copyright © 2018 Teclib. All rights reserved.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      https://flyve-mdm.com
 *  @link      http://flyve.org/android-inventory-library
 *  ---------------------------------------------------------------------
 */

package org.flyve.example_kotlin

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import org.flyve.inventory.InventoryTask

class MainActivity : AppCompatActivity() {

    val LOG = "inventory.example"


    fun showDialogShare(context: Context) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialog_share_title)
        val type = IntArray(1)
        //list of items
        val items = context.resources.getStringArray(R.array.export_list)
        builder.setSingleChoiceItems(items, 0
        ) { dialog, which -> type[0] = which }
        val positiveText = context.getString(android.R.string.ok)
        builder.setPositiveButton(positiveText
        ) { dialog, which ->
            // positive button logic
            InventoryTask(this@MainActivity, this@MainActivity.LOG, false).shareInventory(type[0])
        }
        val negativeText = context.getString(android.R.string.cancel)
        builder.setNegativeButton(negativeText
        ) { dialog, which ->
            // negative button logic
        }
        val dialog = builder.create()
        // display dialog
        dialog.show()
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permission()
        textHello.setOnClickListener({ generateTask() })

        val btnShare = findViewById<Button>(R.id.btnShare)
        btnShare.setOnClickListener { showDialogShare(applicationContext) }

        btnShare.visibility = View.INVISIBLE
    }

    private fun permission() {
        ActivityCompat.requestPermissions(this@MainActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA), 1)
    }

    private fun generateTask() {
        InventoryTask.showFILog = true
        //        val categories: Array<String> = arrayOf("Hardware", "OperatingSystem")
        val appVersion = "example-app-kotlin"
        val inventoryTask = InventoryTask(this@MainActivity, appVersion, false)
        inventoryTask.tag = "Android Lenovo"
        inventoryTask.getXML(object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String) {
                Log.d(LOG, data)
            }

            override fun onTaskError(error: Throwable) {
                Log.e(LOG, error.message)
            }
        })
        inventoryTask.getJSON(object : InventoryTask.OnTaskCompleted {
            override fun onTaskSuccess(data: String?) {
                Log.d(LOG, data)
                //show btn share
                btnShare.visibility = View.VISIBLE
            }

            override fun onTaskError(error: Throwable?) {
                Log.e(LOG, error?.message)
            }

        })
    }
}
