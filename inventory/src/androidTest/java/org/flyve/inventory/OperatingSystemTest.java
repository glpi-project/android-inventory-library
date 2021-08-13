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
import org.flyve.inventory.categories.OperatingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotEquals;


@RunWith(AndroidJUnit4.class)
public class OperatingSystemTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void getBootTime() {
        assertNotEquals("", new OperatingSystem(appContext).getBootTime());
    }

    @Test
    public void getKernelVersion() {
        assertNotEquals("", new OperatingSystem(appContext).getKernelVersion());
    }

    @Test
    public void getTimeZoneShortName() {
        assertNotEquals("", new OperatingSystem(appContext).getTimeZoneShortName());
    }

    @Test
    public void getCurrentTimezoneOffset() {
        assertNotEquals("", new OperatingSystem(appContext).getCurrentTimezoneOffset());
    }

    @Test
    public void getSSHKey() {
        assertNotEquals("", new OperatingSystem(appContext).getSSHKey());
    }
}