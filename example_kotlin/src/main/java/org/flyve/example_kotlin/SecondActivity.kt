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
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
    }
}
