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

import org.flyve.inventory.categories.Categories;
import org.flyve.inventory.categories.Category;

import org.flyve.inventory.categories.CategoryValue;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CreateFileTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void createXMLTest() throws Exception {
        ArrayList<Categories> mContent = new ArrayList<Categories>();
        Category category = new Category("CAMERAS", "cameras");
        category.put("RESOLUTION", new CategoryValue("3264x2448", "RESOLUTION", "resolution"));

        Categories categories = new Categories(appContext);
        categories.add(category);

        mContent.add(categories);

        try {
            String xml = Utils.createXML(appContext, mContent, "Agent", false, "test-tag");
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

    @Test
    public void createJSONTest() throws Exception {
        ArrayList<Categories> mContent = new ArrayList<Categories>();
        Category category = new Category("CAMERAS", "cameras");
        category.put("RESOLUTION", new CategoryValue("3264x2448", "RESOLUTION", "resolution"));

        Categories categories = new Categories(appContext);
        categories.add(category);

        mContent.add(categories);

        String json = Utils.createJSON(appContext, mContent, "Agent", false, "test-tag");

        try {
            JSONObject objJson = new JSONObject(json);
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

}