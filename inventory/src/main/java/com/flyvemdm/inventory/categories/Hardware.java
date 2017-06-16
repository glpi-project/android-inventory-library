/**
 *
 * Copyright 2017 Teclib.
 * Copyright 2010-2016 by the FusionInventory Development
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

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.format.DateFormat;

import java.util.Properties;

/**
 * This class get all the information of the Environment
 */
public class Hardware extends Categories {

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
    private static final long serialVersionUID = 3528873342443549732L;

//    <!ELEMENT HARDWARE (USERID, OSVERSION, PROCESSORN, OSCOMMENTS,
//                        NAME, PROCESSORS, SWAP, ETIME, TYPE, OSNAME, IPADDR,
//                        WORKGROUP, DESCRIPTION, MEMORY, UUID, DNS, LASTLOGGEDUSER,
//                        USERDOMAIN, DATELASTLOGGEDUSER, DEFAULTGATEWAY, VMSYSTEM, WINOWNER,
//                        WINPRODID, WINPRODKEY, WINCOMPANY, WINLANG, CHASSIS_TYPE, VMNAME,
//                        VMHOSTSERIAL, ARCHNAME)>

    private Properties props;
    private Context xCtx;

    /**
     * This constructor load the context and the Hardware information
     * @param xCtx Context where this class work
     */
    public Hardware(Context xCtx) {
        super(xCtx);

        this.xCtx = xCtx;
        props = System.getProperties();
        Memory memory = new Memory(xCtx);

        Category c = new Category(mCtx,"HARDWARE");

        c.put("DATELASTLOGGEDUSER", getDatelastloggeduser() );
        c.put("LASTLOGGEDUSER", getLastloggeduser());
        c.put("NAME", getName());
        c.put("OSNAME", getOsname());
        c.put("OSVERSION", getOsversion());
        c.put("ARCHNAME", getArchname());
        c.put("UUID", getUUID());
        c.put("MEMORY", memory.getCapacity());

        this.add(c);

    }

    public String getDatelastloggeduser() {
        return String.valueOf(DateFormat.format("MM/dd/yy", Build.TIME));
    }

    public String getLastloggeduser() {
        String mLastloggeduser = "";
        if (!Build.USER.equals(Build.UNKNOWN)) {
            mLastloggeduser = Build.USER;
        } else {
            String user = props.getProperty("user.name");
            if (!user.equals("")) {
                mLastloggeduser = props.getProperty("user.name");
            }
        }

        return mLastloggeduser;
    }

    public String getName() {
        return Build.MODEL;
    }

    public String getOsname() {
        return "Android";
    }

    public String getOsversion() {
        return Build.VERSION.RELEASE;
    }

    public String getArchname() {
        return props.getProperty("os.arch");
    }

    public String getUUID() {
        return Secure.getString(xCtx.getContentResolver(), Secure.ANDROID_ID);
    }

}
