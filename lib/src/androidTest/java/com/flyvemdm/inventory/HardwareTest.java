package com.flyvemdm.inventory;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.flyvemdm.inventory.categories.Hardware;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;

/*
 *   Copyright © 2017 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android
 *
 * flyve-mdm-android is a subproject of Flyve MDM. Flyve MDM is a mobile
 * device management software.
 *
 * Flyve MDM is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * Flyve MDM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @author    rafaelhernandez
 * @date      15/6/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */
@RunWith(AndroidJUnit4.class)
public class HardwareTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getDatelastloggeduser() throws Exception {

        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getDatelastloggeduser());

    }

    @Test
    public void getLastloggeduser() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getLastloggeduser());
    }

    @Test
    public void getName() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getName());
    }

    @Test
    public void getOsversion() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getOsversion());
    }

    @Test
    public void getArchname() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getArchname());
    }

    @Test
    public void getUUID() throws Exception {
        Hardware hardware = new Hardware(appContext);
        assertNotEquals("", hardware.getUUID());
    }

}