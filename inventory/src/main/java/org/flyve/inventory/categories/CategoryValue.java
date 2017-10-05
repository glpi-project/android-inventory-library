package org.flyve.inventory.categories;

/*
 *   Copyright © 2017 Teclib. All rights reserved.
 *
 *   This file is part of flyve-mdm-android-inventory
 *
 * flyve-mdm-android-inventory is a subproject of Flyve MDM. Flyve MDM is a mobile
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
 * @date      5/10/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android-inventory
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */
public class CategoryValue {

    private String value;
    private String jsonName;
    private String xmlName;

    public CategoryValue(){

    }

    public CategoryValue(String value, String xmlName, String jsonName) {
        if(value==null) {
            value = "";
        }

        if(xmlName==null) {
            xmlName = "";
        }

        if(jsonName==null) {
            jsonName = "";
        }

        this.value = value;
        this.jsonName = jsonName;
        this.xmlName = xmlName;
    }

    public String getValue() {
        return value;
    }

    public String getJsonName() {
        return jsonName;
    }

    public String getXmlName() {
        return xmlName;
    }
}
