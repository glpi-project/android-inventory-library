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

package org.flyve.inventory.categories;

import android.content.Context;
import android.os.Build;
import android.os.UserManager;

import org.flyve.inventory.FILog;

import java.util.Properties;

/**
 * This class get all the information of the Environment
 */
public class User extends Categories {

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

    private Properties props;
    private Context xCtx;

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
        hash = 89 * hash + (this.xCtx != null ? this.xCtx.hashCode() : 0);
        hash = 89 * hash + (this.props != null ? this.props.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the context and the Hardware information
     * @param xCtx Context where this class work
     */
    public User(Context xCtx) {
        super(xCtx);

        this.xCtx = xCtx;

        try {
            props = System.getProperties();

            Category c = new Category("USER", "user");
            c.put("LOGIN", new CategoryValue(userName(), "LOGIN", "login"));

            this.add(c);
        } catch (Exception ex) {
            FILog.e(ex.getMessage());
        }

    }

    /**
     * Get the user name
     */
    public String userName() {
        String userName;
        if(Build.VERSION.SDK_INT >= 17) {
            UserManager userMgr = (UserManager) xCtx.getSystemService(Context.USER_SERVICE);
            if (userMgr != null) {
                try {
                    // validate permission exception
                    userName = userMgr.getUserName();
                } catch (Exception ex) {
                    FILog.e(ex.getMessage());
                    userName = Build.USER;
                }
            } else {
                userName = Build.USER;
            }
        } else {
            userName = Build.USER;
        }

        return userName;
    }

}
