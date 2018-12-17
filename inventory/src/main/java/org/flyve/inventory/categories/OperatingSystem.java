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
import android.os.Build;
import android.os.SystemClock;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.CryptoUtil;
import org.flyve.inventory.FlyveLog;
import org.flyve.inventory.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 * This class get all the information of the Environment
 */
public class OperatingSystem extends Categories {

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
    private Context context;

    /**
     * Indicates whether some other object is "equal to" this one
     *
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
     *
     * @return int a hash code value for the object
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 89 * hash + (this.context != null ? this.context.hashCode() : 0);
        hash = 89 * hash + (this.props != null ? this.props.hashCode() : 0);
        return hash;
    }

    /**
     * This constructor load the context and the Hardware information
     *
     * @param xCtx Context where this class work
     */
    public OperatingSystem(Context xCtx) {
        super(xCtx);

        this.context = xCtx;

        try {
            props = System.getProperties();

            Category c = new Category("OPERATINGSYSTEM", "operatingSystem");
            String architecture = "";
            String hostId = "";

            ArrayList<HashMap<String, String>> arr = Utils.getDeviceProperties();
            for (int i = 0; i < arr.size(); i++) {
                HashMap<String, String> map = arr.get(i);

                if (map.get("ro.product.cpu.abilist")!=null) {
                    if(architecture.trim().isEmpty()) {
                        architecture = map.get("ro.product.cpu.abilist");
                    }
                }

                if (map.get("ro.product.cpu.abilist64")!=null) {
                    if(architecture.trim().isEmpty()) {
                        architecture = map.get("ro.product.cpu.abilist64");
                    }
                }

                if (map.get("net.hostname") != null) {
                    if (architecture.trim().isEmpty()) {
                        hostId = map.get("net.hostname");
                    }
                }
            }

            c.put("ARCH", new CategoryValue(architecture.trim(), "ARCH", "architecture"));
            // review SystemClock.elapsedRealtime()
            c.put("BOOT_TIME", new CategoryValue(getBootTime(), "BOOT_TIME", "bootTime"));
            c.put("DNS_DOMAIN", new CategoryValue(" ", "DNS_DOMAIN", "dnsDomain"));
            c.put("FQDN", new CategoryValue(" ", "FQDN", "FQDN"));
            String fullName = getAndroidVersion(Build.VERSION.SDK_INT) + " api " + Build.VERSION.SDK_INT;
            c.put("FULL_NAME", new CategoryValue(fullName, "FULL_NAME", "fullName"));
            c.put("HOSTID", new CategoryValue(hostId, "HOSTID", "hostId"));
            c.put("KERNEL_NAME", new CategoryValue("linux", "KERNEL_NAME", "kernelName"));
            c.put("KERNEL_VERSION", new CategoryValue(getKernelVersion(), "KERNEL_VERSION", "kernelVersion"));
            c.put("NAME", new CategoryValue(getAndroidVersion(Build.VERSION.SDK_INT), "NAME", "Name"));
            c.put("SSH_KEY", new CategoryValue(getSSHKey(), "SSH_KEY", "sshKey"));
            c.put("VERSION", new CategoryValue(String.valueOf(Build.VERSION.SDK_INT), "VERSION", "Version"));
            Category category = new Category("TIMEZONE", "timezone");
            category.put("NAME", new CategoryValue( getTimeZoneShortName(), "NAME", "name"));
            category.put("OFFSET", new CategoryValue(getCurrentTimezoneOffset(), "OFFSET", "offset"));
            c.put("TIMEZONE", new CategoryValue(category));

            this.add(c);

        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.OPERATING_SYSTEM, ex.getMessage()));
        }
    }

    public String getSSHKey() {
        String encryptedMessage = "N/A";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Map keyPair = CryptoUtil.generateKeyPair();
                String publicKey = (String) keyPair.get("publicKey");
                encryptedMessage = CryptoUtil.encrypt("Test message...", publicKey);
            }
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.OPERATING_SYSTEM_SSH_KEY, ex.getMessage()));
        }
        return "ssh-rsa " + encryptedMessage;
    }

    public String getBootTime() {
        String value = "N/A";
        try {
            long milliSeconds = System.currentTimeMillis() - SystemClock.elapsedRealtime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm", Locale.getDefault());
            Date resultDate = new Date(milliSeconds);
            value = sdf.format(resultDate);
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.OPERATING_SYSTEM_BOOT_TIME, ex.getMessage()));
        }
        return value;
    }

    public String getKernelVersion() {
        String value = "N/A";
        try {
            Process p = Runtime.getRuntime().exec("uname -a");
            InputStream is;
            if (p.waitFor() == 0) {
                is = p.getInputStream();
            } else {
                return value;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is),
                    64);
            String line = br.readLine();
            br.close();
            value = line;
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.OPERATING_SYSTEM_KERNEL, ex.getMessage()));
        }
        return value;
    }

    public String getTimeZoneShortName() {
        String value = "N/A";
        try {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Calendar calendar = Calendar.getInstance(timeZone);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            simpleDateFormat.setTimeZone(timeZone);

            FlyveLog.i("Time zone: " + timeZone.getID());
            FlyveLog.i("default time zone: " + TimeZone.getDefault().getID());

            FlyveLog.i("UTC:     " + simpleDateFormat.format(calendar.getTime()));
            FlyveLog.i("Default: " + calendar.getTime());
            value = timeZone.getDisplayName();
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.OPERATING_SYSTEM_TIME_ZONE, ex.getMessage()));
        }
        return  value;
    }

    public String getCurrentTimezoneOffset() {
        String value = "N/A";
        try {
            TimeZone tz = TimeZone.getDefault();
            Calendar cal = GregorianCalendar.getInstance(tz);
            int offsetInMillis = tz.getOffset(cal.getTimeInMillis());

            int abs = Math.abs(offsetInMillis / 3600000);
            int abs1 = Math.abs((offsetInMillis / 60000) % 60);
            /*String offset = String.format(Locale.getDefault(), "%02d:%02d", abs, abs1);*/
            String offset = abs + "" + abs1;
            offset = (offsetInMillis >= 0 ? "+" : "-") + offset;

            value = offset;
        } catch (Exception ex) {
            FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.OPERATING_SYSTEM_CURRENT, ex.getMessage()));
        }
        return value;
    }

    private String getAndroidVersion(final int sdk) {

        String version = "N/A";
        try
        {
            SDK sdkEnum = SDK.values()[ sdk - 1 ];
            version = sdkEnum.toString();
        }
        finally
        {
            return version;
        }
    }

    private static enum SDK{

        SDK1("","1.0"),
        SDK2(NameOS.PETIT_FOUR,"1.1"),
        SDK3(NameOS.CUPCAKE,"1.5"),
        SDK4(NameOS.DONUT,"1.6"),
        SDK5(NameOS.ECLAIR,"2.0"),
        SDK6(NameOS.ECLAIR,"2.0.1"),
        SDK7(NameOS.ECLAIR,"2.1"),
        SDK8(NameOS.FROYO,"2.2"),
        SDK9(NameOS.GINGERBREAD,"2.3"),
        SDK10(NameOS.GINGERBREAD,"2.3.3"),
        SDK11(NameOS.HONEYCOMB,"3.0"),
        SDK12(NameOS.HONEYCOMB,"3.1"),
        SDK13(NameOS.HONEYCOMB,"3.2"),
        SDK14(NameOS.ICE_CREAM_SANDWICH,"4.0"),
        SDK15(NameOS.ICE_CREAM_SANDWICH,"4.0.3"),
        SDK16(NameOS.JELLY_BEAN,"4.1"),
        SDK17(NameOS.JELLY_BEAN,"4.2"),
        SDK18(NameOS.JELLY_BEAN,"4.3"),
        SDK19(NameOS.KITKAT,"4.4"),
        SDK20(NameOS.KITKAT_WATCH,"4.4"),
        SDK21(NameOS.LOLLIPOP,"5.0"),
        SDK22(NameOS.LOLLIPOP,"5.1"),
        SDK23(NameOS.MARSHMALLOW,"6.0"),
        SDK24(NameOS.NOUGAT,"7.0"),
        SDK25(NameOS.NOUGAT,"7.1.1"),
        SDK26(NameOS.OREO,"8.0"),
        SDK27(NameOS.OREO,"8.1");

        private final String name;
        private final String version;
        private static final String ANDROID="Android";

        SDK( final String name,final String version){

            this.name=name;
            this.version=version;
        }

        @Override
        public String toString() {

            return (name+" "+ANDROID+" "+version).trim();
        }

        private static final class NameOS{

            public static final String PETIT_FOUR="Petit Four";
            public static final String CUPCAKE="Cupcake";
            public static final String DONUT="Donut";
            public static final String ECLAIR = "Eclair";
            public static final String FROYO="Froyo";
            public static final String GINGERBREAD="Gingerbread";
            public static final String HONEYCOMB="Honeycomb";
            public static final String ICE_CREAM_SANDWICH="Ice Cream Sandwich";
            public static final String JELLY_BEAN="Jelly Bean";
            public static final String KITKAT="KitKat";
            public static final String KITKAT_WATCH="KitKat Watch";
            public static final String LOLLIPOP = "Lollipop";
            public static final String MARSHMALLOW="Marshmallow";
            public static final String NOUGAT="Nougat";
            public static final String OREO="Oreo";
        }

    }
}