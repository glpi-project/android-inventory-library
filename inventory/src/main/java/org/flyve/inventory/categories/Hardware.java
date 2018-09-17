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

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings.Secure;
import android.text.format.DateFormat;

import org.flyve.inventory.FILog;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
    private static final String OSNAME = "Android";
    private UserManager um;

    /**
     * Indicates whether some other object is "equal to" this one
     * @param Object obj the reference object with which to compare
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
        hash = 89 * hash + (this.xCtx != null ? this.xCtx.hashCode() : 0);
        hash = 89 * hash + (this.props != null ? this.props.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the context and the Hardware information
     * @param xCtx Context where this class work
     */
    public Hardware(Context xCtx) {
        super(xCtx);

        this.xCtx = xCtx;

        try {
            props = System.getProperties();
            Memory memory = new Memory(xCtx);
            getUserInfo(xCtx);

            Category c = new Category("HARDWARE", "hardware");

            c.put("DATELASTLOGGEDUSER", new CategoryValue(getDatelastloggeduser(), "DATELASTLOGGEDUSER", "dateLastLoggedUser"));
            c.put("LASTLOGGEDUSER", new CategoryValue(getCmd().toString(), "LASTLOGGEDUSER", "lastLoggedUser"));
            c.put("NAME", new CategoryValue(getName(), "NAME", "name"));
            c.put("OSNAME", new CategoryValue(OSNAME, "OSNAME", "osName"));
            c.put("OSVERSION", new CategoryValue(getOsversion(), "OSVERSION", "osVersion"));
            c.put("ARCHNAME", new CategoryValue(getArchname(), "ARCHNAME", "archName"));
            c.put("UUID", new CategoryValue(getUUID(), "UUID", "uuid"));

            String vMemory = memory.getCapacity();

            c.put("MEMORY", new CategoryValue(vMemory, "MEMORY", "memory"));

            this.add(c);
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

    }

    private void getUserInfo(Context xCtx) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            um = (UserManager) xCtx.getSystemService(Context.USER_SERVICE);
        }
    }

    public ArrayList<String> getCmd() {
        ArrayList<String> names = new ArrayList<>();
        try {
            java.lang.Process script = Runtime.getRuntime().exec(new String[] { "su", "cat /data/system/users/0.xml", "exit" });
            /*java.lang.Process p = Runtime.getRuntime().exec( "su" );
            DataOutputStream in = new DataOutputStream(p.getOutputStream());
            in.writeBytes("cat /data/system/users/o.xml" + "\n");
            in.writeBytes("exit\n");
            in.flush();
            byte[] buffer = new byte[4];
            int read = 0;
            while ((read = in.read(buffer, 0, buffer.length)) != -1) {
                in.read(buffer);
            }*/
            BufferedReader in = new BufferedReader(new InputStreamReader(script.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                names.add(line);
            }
        } catch (Exception ex) {
            names.add("Dumpsys Permission");
            FILog.e(ex.getMessage());
        }
        return names;
    }

    public String numberForUser() {
        String value = "N/A";
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                UserHandle uh = Process.myUserHandle();
                return String.valueOf(um.getSerialNumberForUser(uh));
            } else {
                return "N/A";
            }
        } catch (SecurityException ex) {
            FILog.e(ex.getMessage());
            value = "ID N/A Permission";
        } catch (NullPointerException ex) {
            FILog.e(ex.getMessage());
        }
        return value;
    }

    public String getUserName() {
        String value = "N/A";
        try {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ? um.getUserName() : "N/A";
        } catch (SecurityException ex) {
            FILog.e(ex.getMessage());
            value = "Name N/A Permission";
        } catch (NullPointerException ex) {
            FILog.e(ex.getMessage());
        }
        return value;
    }

    /**
     * Get the date of the last time the user logged
     * @return string the date in simple format
     */
    public String getDatelastloggeduser() {
        return String.valueOf(DateFormat.format("MM/dd/yy", Build.TIME));
    }

    /**
     * Get the name of the last logged user
     * @return string the user name
     */
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

    /**
     * Get the hardware name
     * @return string with the model
     */
    public String getName() {
        return Build.MODEL;
    }

    /**
     * Get the OS version
     * @return string the version
     */
    public String getOsversion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Get the name of the architecture
     * @return string the OS architecture
     */
    public String getArchname() {
        return props.getProperty("os.arch");
    }

    /**
     * Get the Universal Unique Identifier (UUID)
     * @return string the Android ID
     */
    public String getUUID() {
        return Secure.getString(xCtx.getContentResolver(), Secure.ANDROID_ID);
    }

}
