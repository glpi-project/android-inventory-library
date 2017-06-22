package com.flyvemdm.inventory;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.flyvemdm.inventory.categories.Categories;
import com.flyvemdm.inventory.categories.Category;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

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
 * @author    Rafael Hernandez
 * @date      15/6/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */

@RunWith(AndroidJUnit4.class)
public class CreateFileTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void createXMLTest() throws Exception {
        ArrayList<Categories> mContent = new ArrayList<Categories>();
        Category category = new Category("CAMERAS");
        category.put("RESOLUTION","3264x2448");

        Categories categories = new Categories(appContext);
        categories.add(category);

        mContent.add(categories);

        try {
            String xml = Utils.createXML( mContent, "Agent" );
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

    @Test
    public void createJSONTest() throws Exception {
        ArrayList<Categories> mContent = new ArrayList<Categories>();
        Category category = new Category("CAMERAS");
        category.put("RESOLUTION","3264x2448");

        Categories categories = new Categories(appContext);
        categories.add(category);

        mContent.add(categories);

        String json = Utils.createJSON( mContent, "Agent" );

        try {
            JSONObject objJson = new JSONObject(json);
            assertTrue(true);
        } catch (Exception ex) {
            assertTrue(false);
        }
    }

}