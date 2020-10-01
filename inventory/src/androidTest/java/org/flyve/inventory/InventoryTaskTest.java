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

package org.flyve.inventory;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class InventoryTaskTest {
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getJSONTest() throws Exception {
        InventoryTask task = new InventoryTask(appContext, "test", true);
        task.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("Success Library JSON: ", data);
                assertNotEquals("", data);
            }

            @Override
            public void onTaskError(Throwable error) {
                assertTrue(true);
            }
        });
    }

    @Test
    public void getXMLTest() throws Exception {
        InventoryTask task = new InventoryTask(appContext, "test", true);
        task.getXML(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("Success Library XML: ", data);
                assertNotEquals("", data);
            }

            @Override
            public void onTaskError(Throwable error) {
                assertTrue(true);
            }
        });
    }

    @Test
    public void getJSONsyncTest() throws Exception {
        InventoryTask task = new InventoryTask(appContext, "test", true);
        String data = task.getJSONSync();
        assertNotEquals("", data);
    }

    @Test
    public void getXMLsyncTest() throws Exception {
        InventoryTask task = new InventoryTask(appContext, "test", true);
        String data = task.getXMLSyn();
        Log.d("Success Library XML: ", data);
        assertNotEquals("", data);
    }
}
