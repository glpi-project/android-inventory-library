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

import android.os.Build;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
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
     * @param xType String name of the node
     * @param tagName String name of the tag
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
       if (value != null) {
           if (value.getCategory() == null) {
               String s = value.getValue();
               if (s != null && !s.equals(Build.UNKNOWN)){
                   if(s.equalsIgnoreCase("N/A") || s.equalsIgnoreCase("N\\/A")){
                       value.setValue("");
                   }
                   return super.put(key, value);
               } else if (value.getValues() != null && value.getValues().size() > 0) {
                   return super.put(key, value);
               } else {
                   return null;
               }
           } else {
               return super.put(key, value);
           }
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
                setXMLValues(serializer, entry);
            }

            serializer.endTag(null, mType);
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(String.valueOf(CommonErrorType.CATEGORY_TO_XML), ex.getMessage()));
        }
    }

    public void toXMLWithoutPrivateData(XmlSerializer serializer) {
        try {
            serializer.startTag(null, mType);

            for (Map.Entry<String, CategoryValue> entry : this.entrySet()) {
                if(this.get(entry.getKey()).isPrivate() != null
                        && !this.get(entry.getKey()).isPrivate()) {
                    setXMLValues(serializer, entry);
                }
            }

            serializer.endTag(null, mType);
        } catch (Exception ex) {
            InventoryLog.e(InventoryLog.getMessage(String.valueOf(CommonErrorType.CATEGORY_TO_XML_WITHOUT_PRIVATE), ex.getMessage()));
        }
    }

    private void setXMLValues(XmlSerializer serializer, Map.Entry<String, CategoryValue> data) throws IOException {
        CategoryValue categoryValue = this.get(data.getKey());
        if (categoryValue.getCategory() == null) {
            /* If is a normal value */
            if (categoryValue.getValues() == null || categoryValue.getValues().size() <= 0) {
                String xmlName = categoryValue.getXmlName();
                String value = categoryValue.getValue();
                Boolean hasCDATA = categoryValue.hasCDATA();
                setChildXMLValue(serializer, xmlName, value, hasCDATA);
            /* If is a list of values */
            } else {
                for (String value : categoryValue.getValues()) {
                    String xmlName = categoryValue.getXmlName();
                    Boolean hasCDATA = categoryValue.hasCDATA();
                    setChildXMLValue(serializer, xmlName, value, hasCDATA);
                }
            }
        /* If is a Category embed */
        } else {
            Category category = categoryValue.getCategory();
            serializer.startTag(null, category.getType());
            if (categoryValue.getValues() != null && categoryValue.getValues().size() > 0) {
                for (Entry<String, CategoryValue> entries : category.entrySet()) {
                    String xmlName = entries.getKey();
                    for (String value : category.get(xmlName).getValues()) {
                        setChildXMLValue(serializer, xmlName, value, category.get(xmlName).hasCDATA());
                    }
                }
            } else {
                for (Entry<String, CategoryValue> entries : category.entrySet()) {
                    String xmlName = entries.getKey();
                    String value = category.get(xmlName).getValue();
                    setChildXMLValue(serializer, xmlName, value, category.get(xmlName).hasCDATA());
                }
            }
            serializer.endTag(null, category.getType());
        }
    }

    private void setChildXMLValue(XmlSerializer serializer, String xmlName, String xmlValue, boolean hasCData) throws IOException {
        serializer.startTag(null, xmlName);
        String textValue;
        textValue = hasCData ? "<![CDATA[" + String.valueOf(xmlValue) + "]]>" : String.valueOf(xmlValue);
        serializer.text(textValue);
        serializer.endTag(null, xmlName);
    }

    /**
     * This is a public function that create a JSON
     * @return JSONObject
     */
    public JSONObject toJSON() {
        try {
            JSONObject jsonCategories = new JSONObject();
            for (Map.Entry<String,CategoryValue> entry : this.entrySet()) {
                setJSONValues(jsonCategories, entry);
            }

            return jsonCategories;
        } catch ( Exception ex ) {
            InventoryLog.e(InventoryLog.getMessage(String.valueOf(CommonErrorType.CATEGORY_TO_JSON), ex.getMessage()));
            return new JSONObject();
        }
    }

    public JSONObject toJSONWithoutPrivateData() {
        try {
            JSONObject jsonCategories = new JSONObject();
            for (Map.Entry<String,CategoryValue> entry : this.entrySet()) {
                if(this.get(entry.getKey()).isPrivate() != null
                        && !this.get(entry.getKey()).isPrivate()) {
                    setJSONValues(jsonCategories, entry);
                }
            }

            return jsonCategories;
        } catch ( Exception ex ) {
            InventoryLog.e(InventoryLog.getMessage(String.valueOf(CommonErrorType.CATEGORY_TO_JSON_WITHOUT_PRIVATE), ex.getMessage()));
            return new JSONObject();
        }
    }

    private void setJSONValues(JSONObject jsonCategories, Map.Entry<String, CategoryValue> data) throws JSONException {
        CategoryValue categoryValue = this.get(data.getKey());
        if (data.getValue().getCategory() == null) {
            if (data.getValue().getValues() == null) {
                jsonCategories.put(categoryValue.getJsonName(), categoryValue.getValue());
            } else {
                jsonCategories.put(categoryValue.getJsonName(), categoryValue.getValues());
            }
        } else {
            Category category = categoryValue.getCategory();
            JSONObject newCategory = new JSONObject();
            for (CategoryValue value : category.values()) {
                if (data.getValue().getValues() == null) {
                    newCategory.put(value.getJsonName(), value.getValue());
                } else {
                    newCategory.put(value.getJsonName(), value.getValues());
                }
            }
            jsonCategories.put(category.getType(), newCategory);
        }
    }
}
