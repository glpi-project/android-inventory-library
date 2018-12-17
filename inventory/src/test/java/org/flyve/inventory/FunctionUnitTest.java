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

import org.flyve.inventory.categories.StringUtils;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class FunctionUnitTest {

    @Test
    public void createIP_correct() throws Exception {
        int ip = 19216801;
        String formatIp = StringUtils.intToIp(ip);
        assertTrue(formatIp.split("\\.").length==4);
    }

    @Test
    public void intToByte_correct() throws Exception {
        int byteNumber = 19216801;

        try {
            byte[] byteFormat = StringUtils.intToByte(byteNumber);
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

    @Test
    public void join_correct() throws Exception {
        ArrayList<String> arr = new ArrayList<String>();
        for (int i =2; i <= 12; i++) {
            arr.add(String.valueOf(i*12345));
        }

        assertTrue(StringUtils.join(arr, "#").split("#").length==11);
    }
}