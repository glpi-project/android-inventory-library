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
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.flyve.inventory.categories.Cameras;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class CamerasTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getResolutions() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                assertNotEquals("", cameras.getResolution(chars));
            }
        }
    }

    @Test
    public void getFacingState() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                assertNotEquals("", cameras.getFacingState(chars));
            }
        }
    }

    @Test
    public void getCategoryImageFormat() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                ArrayList<String> imageFormat = cameras.getImageFormat(chars);
                for (String image : imageFormat) {
                    assertNotEquals("", image);
                }
            }
        }
    }

    @Test
    public void getFlashUnit() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                assertNotEquals("", cameras.getFlashUnit(chars));
            }
        }
    }

    @Test
    public void getOrientation() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                assertNotEquals("", cameras.getOrientation(chars));
            }
        }
    }

    @Test
    public void getVideoResolution() {
        int count = Camera.getNumberOfCameras();

        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < count; index++) {
            assertNotEquals("", cameras.getVideoResolution(0));
        }
    }

    @Test
    public void getFocalLength() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                assertNotEquals("", cameras.getFocalLength(chars));
            }
        }
    }

    @Test
    public void getSensorSize() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            CameraCharacteristics chars = cameras.getCharacteristics(appContext, index);
            if (chars != null) {
                assertNotEquals("", cameras.getSensorSize(chars));
            }
        }
    }

    @Test
    public void getManufacturer() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            assertNotEquals("", cameras.getManufacturer(index));
        }
    }

    @Test
    public void getModel() {
        Cameras cameras = new Cameras(appContext);
        for (int index = 0; index < cameras.getCountCamera(appContext); index++) {
            assertNotEquals("", cameras.getModel(index));
        }
    }

    @Test
    public void getSupportValue() {
        Cameras cameras = new Cameras(appContext);
        assertNotEquals("", cameras.getSupportValue());
    }

}