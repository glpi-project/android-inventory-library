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
import android.hardware.Sensor;
import android.hardware.SensorManager;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.Sensors;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class SensorsTest {

    Context appContext = InstrumentationRegistry.getTargetContext();
    Sensors csensors = new Sensors(appContext);

    @Test
    public void getName() throws Exception {
        SensorManager sensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : sensors) {
            assertNotEquals("", csensors.getName(s));
        }
    }

    @Test
    public void getManufacturer() throws Exception {
        SensorManager sensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : sensors) {
            assertNotEquals("", csensors.getManufacturer(s));
        }
    }

    @Test
    public void getType() throws Exception {
        SensorManager sensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : sensors) {
            assertNotEquals("", csensors.getType(s));
        }
    }

    @Test
    public void getPower() throws Exception {
        SensorManager sensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : sensors) {
            assertNotEquals("", csensors.getPower(s));
        }
    }

    @Test
    public void getVersion() throws Exception {
        SensorManager sensorManager = (SensorManager) appContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : sensors) {
            assertNotEquals("", csensors.getVersion(s));
        }
    }

}