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

package org.flyve.inventory.categories;

import android.content.Context;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.FlyveLog;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;

public class Categories extends ArrayList<Category>{

    /*
     * The serialization runtime associates with each serializable class a version number,
     * called a serialVersionUID, which is used during deserialization to verify that the sender
     * and receiver of a serialized object have loaded classes for that object that are compatible
     * with respect to serialization. If the receiver has loaded a class for the object that has a
     * different serialVersionUID than that of the corresponding sender's class, then deserialization
     * will result in an  InvalidClassException
     *
     *  from: https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
     */
    private static final long serialVersionUID = 2278660715848751766L;
    private Context mCtx;

    /**
     * Indicates whether some other object is "equal to" this one
     * @param obj the reference object with which to compare
     * @return boolean true if the object is the same as the one given in argument
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (!super.equals(obj));
    }

    /**
     * Returns a hash code value for the object
     * @return int a hash code value for the object
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.mCtx != null ? this.mCtx.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the Context of the instance
     * @param xCtx Context where this class work
     */
    public Categories(Context xCtx) {
        mCtx = xCtx;
    }

    /**
     *  Create a XML object
     * @param xSerializer object to create XML
     */
    public void toXML(XmlSerializer xSerializer) {
        try {
            for( Category c : this) {
                c.toXML(xSerializer);
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.CATEGORIES_TO_XML), ex.getMessage()));
        }
    }

    /**
     *  Create a XML object
     * @param xSerializer object to create XML
     */
    public void toXMLWithoutPrivateData(XmlSerializer xSerializer) {
        try {
            for( Category c : this) {
                c.toXMLWithoutPrivateData(xSerializer);
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.CATEGORIES_TO_XML_WITHOUT_PRIVATE), ex.getMessage()));
        }
    }

    public void toJSON(JSONObject json) {
        JSONArray jsonArr = new JSONArray();
        String mType = "";
        try {
            for( Category c : this) {
                mType = c.getTagName();
                jsonArr.put(c.toJSON());
            }

            json.put(mType, jsonArr);
        } catch (Exception ex) {
            FlyveLog.e(CommonErrorType.CATEGORIES_TO_JSON + " " + ex.getMessage());
        }
    }

    public void toJSONWithoutPrivateData(JSONObject json) {
        JSONArray jsonArr = new JSONArray();
        String mType = "";
        try {
            for( Category c : this) {
                mType = c.getTagName();
                jsonArr.put(c.toJSONWithoutPrivateData());
            }

            json.put(mType, jsonArr);
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(String.valueOf(CommonErrorType.CATEGORIES_TO_JSON_WITHOUT_PRIVATE), ex.getMessage()));
        }
    }
}
