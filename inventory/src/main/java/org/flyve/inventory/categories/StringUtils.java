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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * This is a Utility class for String management
 */
public class StringUtils {

    private StringUtils() {

    }

    /**
     * This method can join a Collection of String in a single String separated by delimiter character,
     * can be forward or reversed
     *
     * @param collection Collection of String
     * @param delimiter This is the character that join the String
     * @param reversed Indicate if is forward or reversed the join of the String
     * @return Joined String
     */
    public static String join(Collection<String> collection, String delimiter, boolean reversed) {
        if (collection != null) {
            StringBuilder buffer = new StringBuilder();
            Iterator<String> iter = collection.iterator();
            while (iter.hasNext()) {
                if (!reversed) {
                    buffer.append(iter.next());
                    if (iter.hasNext()) {
                        buffer.append(delimiter);
                    }
                } else {
                    buffer.insert(0, iter.next());
                    if (iter.hasNext()) {
                        buffer.insert(0, delimiter);

                    }
                }
            }
            return buffer.toString();
        } else {
            return null;
        }
    }

    /**
     * This method can join a Collection of String in a single String separated by delimiter character
     * @param collection Collection of String
     * @param delimiter This is the character that join the String
     * @return Joined String
     */
    public static String join(Collection<String> collection, String delimiter) {

        return StringUtils.join(collection, delimiter, false);

    }

    /**
     * Convert int value to byte[]
     * @param value Integer value to convert
     * @return byte[]
     */
    public static byte[] intToByte(int value) {
        return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
    }

    /**
     * Convert int to IP
     * @param value Integer value
     * @return String
     */
    public static String intToIp(int value) {
        byte[] b = intToByte(value);
        ArrayList<String> stack = new ArrayList<String>();
        for (byte c : b) {
            stack.add(String.valueOf(0xFF & c));
        }

        return (StringUtils.join(stack, ".", true));
    }

    /**
     * Convert ipAddress int to SubNetMask
     * @param ipAddress Integer value
     * @return String
     */
    public static String getSubNet(int ipAddress) {
        return  ((ipAddress & 0xFF) + "." +
                ((ipAddress >>>= 8) & 0xFF) + "." +
                ((ipAddress >>>= 8) & 0xFF) + "." +
                ((ipAddress >>>= 8) & 0xFF));
    }
}