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
 *  @copyright Copyright FusionInventory.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.os.Build;

import org.flyve.inventory.FILog;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class create a node of the XML
 */
public class Category extends LinkedHashMap<String, CategoryValue> {

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
    private static final long serialVersionUID = 6443019125036309325L;
    private String mType;
    private String mtagName;

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj Object the reference object with which to compare.
     * @return boolean True or false
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
     * Returns a hash code value for the object.
     * @return int a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.mType != null ? this.mType.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the Context of the instance and the name of the node in XML
     * @param xType name of the node
     */
    public Category(String xType, String tagName) {
        mType = xType;
        mtagName = tagName;
    }

    /**
     * Return the type of category
     * @return String with the category
     */
    public String getType() {
        return mType;
    }

    /**
     * Return the name that will appear on json node
     * @return String with the category
     */
    public String getTagName() {
        return mtagName;
    }

    /**
     *  Put a key, value on a LinkedHashMap object
     *
     * @param key key String
     * @param value value String
     * @return String
     */
    public CategoryValue put(String key, CategoryValue value) {
       //Do not add value if it's null, blank or "unknow"
       if (value != null && !value.getValue().equals("") && !value.getValue().equals(Build.UNKNOWN)) {
    	   return super.put(key, value);
       } else {
    	   return null;
       }
    }

    /**
     * This is a public function that create a XML node with a XmlSerializer object
     * @param serializer object
     */
    public void toXML(XmlSerializer serializer) {
        try {
            serializer.startTag(null, mType);

            for (Map.Entry<String, CategoryValue> entry : this.entrySet()) {
                serializer.startTag(null, this.get(entry.getKey()).getXmlName());

                String value;
                if(this.get(entry.getKey()).hasCDATA()) {
                    value = "<![CDATA[" + String.valueOf(this.get(entry.getKey()).getValue()) + "]]>";
                } else {
                    value = String.valueOf(this.get(entry.getKey()).getValue());
                }

                serializer.text(value);
                serializer.endTag(null, this.get(entry.getKey()).getXmlName());
            }

            serializer.endTag(null, mType);
        } catch (Exception ex) {
            FILog.d(ex.getMessage());
        }
    }

    public void toXMLWithoutPrivateData(XmlSerializer serializer) {
        try {
            serializer.startTag(null, mType);

            for (Map.Entry<String, CategoryValue> entry : this.entrySet()) {
                if(!this.get(entry.getKey()).isPrivate()) {
                    serializer.startTag(null, this.get(entry.getKey()).getXmlName());
                    serializer.text(String.valueOf(this.get(entry.getKey()).getValue()));
                    serializer.endTag(null, this.get(entry.getKey()).getXmlName());
                }
            }

            serializer.endTag(null, mType);
        } catch (Exception ex) {
            FILog.d(ex.getMessage());
        }
    }

    /**
     * This is a public function that create a JSON
     */
    public JSONObject toJSON() {
        try {
            JSONObject jsonCategories = new JSONObject();
            for (Map.Entry<String,CategoryValue> entry : this.entrySet()) {
                jsonCategories.put(this.get(entry.getKey()).getJsonName(), this.get(entry.getKey()).getValue());
            }

            return jsonCategories;
        } catch ( Exception ex ) {
            FILog.e( ex.getMessage() );
            return new JSONObject();
        }
    }

    public JSONObject toJSONWithoutPrivateData() {
        try {
            JSONObject jsonCategories = new JSONObject();
            for (Map.Entry<String,CategoryValue> entry : this.entrySet()) {
                if(!this.get(entry.getKey()).isPrivate()) {
                    jsonCategories.put(this.get(entry.getKey()).getJsonName(), this.get(entry.getKey()).getValue());
                }
            }

            return jsonCategories;
        } catch ( Exception ex ) {
            FILog.e( ex.getMessage() );
            return new JSONObject();
        }
    }
}
