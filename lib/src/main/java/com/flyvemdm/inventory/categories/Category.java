/**
 * FusionInventory
 *
 * Copyright (C) 2010-2017 by the FusionInventory Development Team.
 *
 * http://www.fusioninventory.org/
 * https://github.com/fusioninventory/fusioninventory-android
 *
 * ------------------------------------------------------------------------
 *
 * LICENSE
 *
 * This file is part of FusionInventory project.
 *
 * FusionInventory is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FusionInventory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @update    07/06/2017
 * @license   GPLv2 https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * @link      https://github.com/fusioninventory/fusioninventory-android
 * @link      http://www.fusioninventory.org/
 * ------------------------------------------------------------------------------
 */

package com.flyvemdm.inventory.categories;

import android.os.Build;

import com.flyvemdm.inventory.FILog;
import com.flyvemdm.inventory.FlyveException;

import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class create a node of the XML
 */
public class Category extends LinkedHashMap<String, String>{

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

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param obj Object the reference object with which to compare.
     * @return
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
    public Category(String xType) {
        mType = xType;
    }

    /**
     * Return the type of category
     * @return String with the category
     */
    public String getType() {
        return mType;
    }

    /**
     *  Put a key, value on a LinkedHashMap object
     *
     * @param key key String
     * @param value value String
     * @return String
     */
    public String put(String key, String value) {
       //Do not add value if it's null, blank or "unkown"
       if (value != null && !value.equals("") && !value.equals(Build.UNKNOWN)) {
    	   return super.put(key, value);
       } else {
    	   return "";
       }
    }

    /**
     * This is a private function that create a XML node with a XmlSerializer object
     * @param serializer object
     */
    void toXML(XmlSerializer serializer) throws FlyveException {

        try {
            serializer.startTag(null, mType);

            for (Map.Entry<String, String> entry : this.entrySet()) {
                serializer.startTag(null, entry.getKey());
                serializer.text(String.valueOf(this.get(entry.getKey())));
                serializer.endTag(null, entry.getKey());
            }

            serializer.endTag(null, mType);
        } catch (Exception ex) {
            throw new FlyveException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * This is a private function that create a JSON
     */
    JSONObject toJSON() throws FlyveException {
        try {
            JSONObject jsonCategories = new JSONObject();
            for (Map.Entry<String,String> entry : this.entrySet()) {
                jsonCategories.put(entry.getKey(), String.valueOf(this.get(entry.getKey())));
            }

            return jsonCategories;
        } catch ( Exception ex ) {
            FILog.e( ex.getMessage() );
            throw new FlyveException(ex.getMessage(), ex.getCause());
        }
    }
}
