package com.flyvemdm.inventory;

import android.os.Build;
import android.text.format.DateFormat;
import android.util.Xml;

import com.flyvemdm.inventory.categories.Categories;

import org.json.JSONObject;
import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

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
 * @author    rafaelhernandez
 * @date      21/6/17
 * @copyright Copyright © 2017 Teclib. All rights reserved.
 * @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 * @link      https://github.com/flyve-mdm/flyve-mdm-android
 * @link      https://flyve-mdm.com
 * ------------------------------------------------------------------------------
 */
public class Utils {

    private Utils() {

    }


    /**
     * Create a JSON String with al the Categories available
     * @param mContent ArrayList<Categories> with the categories
     * @return String with JSON
     */
    protected static String createJSON(ArrayList<Categories> mContent, String appVersion) throws FlyveException {

        try {

            JSONObject accountInfo = new JSONObject();
            accountInfo.put("KEYNAME", "TAG");
            accountInfo.put("KEYVALUE", "N/A");

            JSONObject jsonAccessLog = new JSONObject();
            jsonAccessLog.put("LOGDATE", DateFormat.format("yyyy-MM-dd H:mm:ss", new Date()).toString());
            jsonAccessLog.put("USERID", "N/A");

            JSONObject content = new JSONObject();
            content.put("ACCESSLOG", jsonAccessLog);
            content.put("ACCOUNTINFO", jsonAccessLog);

            for (Categories cat : mContent) {
                cat.toJSON(content);
            }

            JSONObject jsonQuery = new JSONObject();
            jsonQuery.put("QUERY", "INVENTORY");
            jsonQuery.put("VERSIONCLIENT", appVersion);
            jsonQuery.put("DEVICEID", Build.SERIAL);
            jsonQuery.put("CONTENT", content);

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("REQUEST", jsonQuery);

            return jsonRequest.toString();

        } catch (Exception ex) {
            FILog.e(ex.getMessage());
            throw new FlyveException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Create a XML String with al the Categories available
     * @param mContent ArrayList<Categories> with the categories
     * @return String with XML
     */
    protected static String createXML(ArrayList<Categories> mContent, String appVersion) throws FlyveException {
        FILog.i("createXML: ");

        if (mContent != null) {
            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            try {
                serializer.setOutput(writer);
                serializer
                        .setFeature(
                                "http://xmlpull.org/v1/doc/features.html#indent-output",
                                true);
                // indentation as 3 spaces

                serializer.startDocument("utf-8", true);
                // Start REQUEST
                serializer.startTag(null, "REQUEST");
                serializer.startTag(null, "QUERY");
                serializer.text("INVENTORY");
                serializer.endTag(null, "QUERY");

                serializer.startTag(null, "VERSIONCLIENT");
                serializer.text(appVersion);
                serializer.endTag(null, "VERSIONCLIENT");

                serializer.startTag(null, "DEVICEID");
                serializer.text(Build.SERIAL);
                serializer.endTag(null, "DEVICEID");

                // Start CONTENT
                serializer.startTag(null, "CONTENT");

                // Start ACCESSLOG
                serializer.startTag(null, "ACCESSLOG");

                serializer.startTag(null, "LOGDATE");
                serializer.text(DateFormat.format("yyyy-MM-dd H:mm:ss", new Date()).toString());
                serializer.endTag(null, "LOGDATE");

                serializer.startTag(null, "USERID");
                serializer.text("N/A");
                serializer.endTag(null, "USERID");

                serializer.endTag(null, "ACCESSLOG");
                // End ACCESSLOG

                serializer.startTag(null, "ACCOUNTINFO");
                serializer.startTag(null, "KEYNAME");
                serializer.text("TAG");
                serializer.endTag(null, "KEYNAME");
                serializer.startTag(null, "KEYVALUE");

                serializer.text("");
                serializer.endTag(null, "KEYVALUE");
                serializer.endTag(null, "ACCOUNTINFO");

                for (Categories cat : mContent) {

                    cat.toXML(serializer);
                }

                serializer.endTag(null, "CONTENT");
                // End CONTENT
                serializer.endTag(null, "REQUEST");
                // End REQUEST

                serializer.endDocument();

                // Return XML String
                return writer.toString();

            } catch (Exception ex) {
                FILog.e(ex.getMessage());
                throw new FlyveException(ex.getMessage(), ex.getCause());
            }
        }

        return "";
    }

}
