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
 *  @author    Ivan del Pino    - <idelpino@teclib.com>
 *  @copyright Copyright Teclib. All rights reserved.
 *  @copyright Copyright FusionInventory.
 *  @license   GPLv3 https://www.gnu.org/licenses/gpl-3.0.html
 *  @link      https://github.com/flyve-mdm/android-inventory-library
 *  @link      http://flyve.org/android-inventory-library/
 *  @link      https://flyve-mdm.com
 *  ---------------------------------------------------------------------
 */

package org.flyve.inventory.categories;

import android.app.Service;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.FlyveLog;

import java.io.File;
import java.math.BigInteger;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

/**
 * This class get all the information of the Network
 */
public class Networks extends Categories {

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
	private static final long serialVersionUID = 6829495385432791427L;

	// Properties of this component
	private static final String TYPE = "WIFI";
	private DhcpInfo dhcp;
	private WifiInfo wifi;
	private final Context context;

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
		hash = 89 * hash + (this.dhcp != null ? this.dhcp.hashCode() : 0);
		hash = 89 * hash + (this.wifi != null ? this.wifi.hashCode() : 0);
		return hash;
	}

	/**
	 * This constructor load the context and the Networks information
	 * @param xCtx Context where this class work
	 */
	public Networks(Context xCtx) {
		super(xCtx);

		context = xCtx;
		try {
			WifiManager pWM = (WifiManager) context.getApplicationContext().getSystemService(Service.WIFI_SERVICE);
			boolean wasWifiEnabled = pWM.isWifiEnabled();

			// Enable Wifi State if not
			if (!wasWifiEnabled) {
				pWM.setWifiEnabled(true);
			}
			Category c = new Category("NETWORKS", "networks");
			c.put("TYPE", new CategoryValue(TYPE, "TYPE", "type"));

			dhcp = pWM.getDhcpInfo();
			wifi = pWM.getConnectionInfo();

			FlyveLog.d("<===WIFI DHCP===>");
			FlyveLog.d("dns1=" + StringUtils.intToIp(dhcp.dns1));
			FlyveLog.d("dns2=" + StringUtils.intToIp(dhcp.dns2));
			FlyveLog.d("leaseDuration=" + dhcp.leaseDuration);

			c.put("MACADDR", new CategoryValue(getMacAddress(), "MACADDR", "macAddress"));
			c.put("SPEED", new CategoryValue(getSpeed(), "SPEED", "speed"));
			c.put("BSSID", new CategoryValue(getBSSID(), "BSSID", "bssid"));
			c.put("SSID", new CategoryValue(getSSID(), "SSID", "ssid"));
			c.put("IPGATEWAY", new CategoryValue(getIpgateway(), "IPGATEWAY", "ipGateway"));
			c.put("IPADDRESS", new CategoryValue(getIpAddress(), "IPADDRESS", "ipAddress", true, false));
			c.put("IPMASK", new CategoryValue(getIpMask(), "IPMASK", "ipMask", true, false));
			c.put("IPDHCP", new CategoryValue(getIpDhCp(), "IPDHCP", "ipDhcp", true, false));
			c.put("IPSUBNET", new CategoryValue(getIpSubnet(), "IPSUBNET", "ipSubnet", true, false));
			c.put("STATUS", new CategoryValue(getStatus(), "STATUS", "status", true, false));
			c.put("DESCRIPTION", new CategoryValue(getDescription(), "DESCRIPTION", "description", true, false));
			c.put("IPADDRESS6", new CategoryValue(getLocalIpV6(), "IPADDRESS6", "ipaddress6", true, false));

			this.add(c);
			// Restore Wifi State
			if (!wasWifiEnabled) {
				pWM.setWifiEnabled(false);
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS, ex.getMessage()));
		}
	}

	/**
	 * Get the Media Access Control address
	 * @return string the MAC address
	 */
	public String getMacAddress() {
		String macAddress = "N/A";
		try {
			macAddress = wifi.getMacAddress();

			// if get default mac address
			if (macAddress == null || macAddress.contains("02:00:00:00:00:00")) {
				macAddress = getMACAddress("wlan0");
				if (macAddress.isEmpty()) {
					macAddress = getMACAddress("eth0");
				}
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_MAC_ADDRESS, ex.getMessage()));
		}

		return macAddress;
	}

	private String getMACAddress(String interfaceName) {
		try {
			List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface intf : interfaces) {
				if (interfaceName != null) {
					if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
				}
				byte[] mac = intf.getHardwareAddress();
				if (mac==null) return "";
				StringBuilder buf = new StringBuilder();
				for (byte aMac : mac) buf.append(String.format("%02X:",aMac));
				if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
				return buf.toString();
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_MAC_ADDRESS_VALUE, ex.getMessage()));
		}

		return "N/A";
	}

	/**
	 * Get the speed of the Wifi connection
	 * @return string the current speed in Mbps
	 */
	public String getSpeed() {
		String value = "N/A";
		try {
			value = String.valueOf(wifi.getLinkSpeed());
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_SPEED, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the Basic Service Set Identifier (BSSID)
	 * @return string the BSSID of the current access point
	 */
	public String getBSSID() {
		String value = "N/A";
		try {
			value = String.valueOf(wifi.getBSSID());
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_BSS_ID, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the Service Set Identifier (SSID)
	 * @return string the SSID of the current network
	 * 
	 */
	public String getSSID() {
		String value = "N/A";
		try {
			value = String.valueOf(wifi.getSSID());
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_SS_ID, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the IP address of the gateway
	 * @return string the gateway IP address
	 */
	public String getIpgateway() {
		String value = "N/A";
		try {
			value = StringUtils.intToIp(dhcp.gateway);
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_IP_GATEWAY, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the IP address
	 * @return string the current IP address
	 */
	public String getIpAddress() {
		String value = "N/A";
		try {
			value = StringUtils.intToIp(dhcp.ipAddress);
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_IP_ADDRESS, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the IP address of the netmask
	 * @return string the netmask
	 */
	public String getIpMask() {
		String value = "N/A";
		try {
			value = StringUtils.intToIp(dhcp.netmask);
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_IP_MASK, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the IP address of the DHCP
	 * @return string the server address
	 */
	public String getIpDhCp() {
		String value = "N/A";
		try {
			value = StringUtils.intToIp(dhcp.serverAddress);
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_IP_DH_CP, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the IP Subnet of the wifi connection info
	 * @return string the IP Subnet
	 */
	public String getIpSubnet() {
		String value = "N/A";
		try {
			value = StringUtils.getSubNet(wifi.getIpAddress());
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_IP_SUBNET, ex.getMessage()));
		}
		return value;
	}

	/**
	 * Get the IP Subnet of the wifi connection info
	 * @return string the IP Subnet
	 */
	public String getStatus() {
		String value = "N/A";
		try {
			value = getCatInfo("/sys/class/net/wlan0/operstate");
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_STATUS, ex.getMessage()));
		}
		return value;
	}

	private String getCatInfo(String path) {
		String value = "N/A";
		try {
			Scanner s = new Scanner(new File(path));
			value = s.next();
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_CAT_INFO, ex.getMessage()));
		}
		return value;
	}

	public String getDescription() {
		String name = "N/A";
		try {
			int ipAddress = wifi.getIpAddress();
			byte[] ip = BigInteger.valueOf(ipAddress).toByteArray();
			InetAddress inetAddress = InetAddress.getByAddress(ip);
			NetworkInterface netInterface = NetworkInterface.getByInetAddress(inetAddress);
			if (netInterface != null ) {
				name = netInterface.getDisplayName();
				return name;
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_DESCRIPTION, ex.getMessage()));
		}
		return name;
	}

	public String getLocalIpV6() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					System.out.println("ip1--:" + inetAddress);
					System.out.println("ip2--:" + inetAddress.getHostAddress());

					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (Exception ex) {
			FlyveLog.e(FlyveLog.getMessage(context, CommonErrorType.NETWORKS_LOCAL_IPV6, ex.getMessage()));
		}
		return "N/A";
	}
}
