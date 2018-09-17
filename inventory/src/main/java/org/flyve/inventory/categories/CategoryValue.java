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
 *  @author    Rafael Hernandez - <rhernandez@teclib.com>
 *  @copyright Copyright Teclib. All rights reserved.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

public class CategoryValue {

    private String value;
    private String jsonName;
    private String xmlName;
    private Boolean isPrivate;
    private Boolean hasCDATA;

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
        this.isPrivate = false;
        this.hasCDATA = false;
    }

    public CategoryValue(String value, String xmlName, String jsonName, Boolean isPrivate, Boolean hasCDATA) {
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
        this.isPrivate = isPrivate;
        this.hasCDATA = hasCDATA;
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

    public Boolean isPrivate() {
        return isPrivate;
    }

    public Boolean hasCDATA() {
        return hasCDATA;
    }
}
