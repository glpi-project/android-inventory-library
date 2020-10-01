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

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.flyve.inventory.CommonErrorType;
import org.flyve.inventory.InventoryLog;
import org.flyve.inventory.Utils;

import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

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
	private static final String NO_DECSRIPTION_PROVIDED = "No description found";
	private DhcpInfo dhcp;
	private WifiInfo wifi;
	private final Context context;

	/**
     * Indicates whether some other object is "equal to" this one
     * @param  obj Object the reference object with which to compare
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

			dhcp = pWM.getDhcpInfo();
			wifi = pWM.getConnectionInfo();

			InventoryLog.d("<===WIFI DHCP===>");
			InventoryLog.d("dns1=" + StringUtils.intToIp(dhcp.dns1));
			InventoryLog.d("dns2=" + StringUtils.intToIp(dhcp.dns2));
			InventoryLog.d("leaseDuration=" + dhcp.leaseDuration);

			c.put("DESCRIPTION", new CategoryValue(getDescription(), "DESCRIPTION", "description", true, false));
			c.put("DRIVER", new CategoryValue(TYPE, "DRIVER", "driver", true, false));
			c.put("IPADDRESS", new CategoryValue(getIpAddress(), "IPADDRESS", "ipAddress", true, false));
			c.put("IPADDRESS6", new CategoryValue(getAddressIpV6(), "IPADDRESS6", "ipaddress6", true, false));
			c.put("IPDHCP", new CategoryValue(getIpDhCp(), "IPDHCP", "ipDhcp", true, false));
			c.put("IPGATEWAY", new CategoryValue(getIpgateway(), "IPGATEWAY", "ipGateway"));
			c.put("IPMASK", new CategoryValue(getIpMask(), "IPMASK", "ipMask", true, false));
			c.put("IPMASK6", new CategoryValue(getMaskIpV6(), "IPMASK6", "ipMask6", true, false));
			c.put("IPSUBNET", new CategoryValue(getIpSubnet(), "IPSUBNET", "ipSubnet", true, false));
			c.put("IPSUBNET6", new CategoryValue(getSubnetIpV6(), "IPSUBNET6", "ipSubnet6", true, false));
			c.put("MACADDR", new CategoryValue(getMacAddress(), "MACADDR", "macAddress"));
			c.put("SPEED", new CategoryValue(getSpeed(), "SPEED", "speed"));
			c.put("STATUS", new CategoryValue(getStatus(), "STATUS", "status", true, false));
			c.put("TYPE", new CategoryValue(TYPE, "TYPE", "type"));
			c.put("WIFI_BSSID", new CategoryValue(getSSID(), "WIFI_BSSID", "wifiBssid"));
			c.put("WIFI_SSID", new CategoryValue(getBSSID(), "WIFI_SSID", "wifiSsid"));

			this.add(c);
			// Restore Wifi State
			if (!wasWifiEnabled) {
				pWM.setWifiEnabled(false);
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_MAC_ADDRESS, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_MAC_ADDRESS_VALUE, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_SPEED, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_BSS_ID, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_SS_ID, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_IP_GATEWAY, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_IP_ADDRESS, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_IP_MASK, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_IP_DH_CP, ex.getMessage()));
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
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_IP_SUBNET, ex.getMessage()));
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
			value = Utils.getCatInfo("/sys/class/net/wlan0/operstate");
		}catch (Exception ex){
			InventoryLog.d(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_STATUS, "Unable to get WIFI status from /sys/class/net/wlan0/operstate try with API"));
		}

		if(value.trim().isEmpty() || value.equalsIgnoreCase("N/A")){
			if(isConnectedToWifi()){
				value = "up";
			}else{
				value = "down";
			}
		}

		return value;
	}


	/**
	 * Try to know if device is connected to WIFI
	 * @return true or false
	 */
	private boolean isConnectedToWifi() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
			Network network = connectivityManager.getActiveNetwork();
			NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
			if (capabilities == null) {
				return false;
			}
			return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
		} else {
			NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo == null) {
				return false;
			}
			return networkInfo.isConnected();
		}

	}



	/**
	 * Get description network
	 * @return string description
	 */
	public String getDescription() {
		String name = "N/A";
		try {
			String ipadressString = Utils.getIPAddress(true);
			InetAddress address = InetAddress.getByName(ipadressString);
			NetworkInterface netInterface = NetworkInterface.getByInetAddress(address);
			if (netInterface != null ) {
				name = netInterface.getDisplayName();
				return name;
			}
		} catch (UnknownHostException ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_DESCRIPTION, ex.getMessage()));
		} catch (SocketException ex){
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_DESCRIPTION, ex.getMessage()));
		} catch(Exception ex){
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_DESCRIPTION, ex.getMessage()));
		}

		//change name
		name = NO_DECSRIPTION_PROVIDED;

		return name;
	}

	/**
	 * Get the address IpV6
	 * @return string the address IpV6
	 */
	public String getAddressIpV6() {
		InterfaceAddress interfaceIPV6 = getInterfaceByType("IPV6");
		if (interfaceIPV6 != null) {
			String address = interfaceIPV6.getAddress().getHostAddress();
			return address.contains("%") ? address.split("%", 2)[0] : "N/A";
		} else {
			return "N/A";
		}
	}


	/**
	 * Get mask IpV6
	 * @return string the mask IpV6
	 */
	public String getMaskIpV6() {
		InterfaceAddress interfaceIPV6 = getInterfaceByType("IPV6");
		if (interfaceIPV6 != null) {
			short prefixLength = interfaceIPV6.getNetworkPrefixLength();
			return getMaskConvert(prefixLength);
		} else {
			return "N/A";
		}
	}

	private String getMaskConvert(short prefixLength) {
		/* Get Binary */
		int resident = prefixLength % 4;
		StringBuilder maskTemp = new StringBuilder();
		StringBuilder maskHexadecimal = new StringBuilder();
		ArrayList<String> maskBinaryList = new ArrayList<>();
		for (int i = 1; i < prefixLength + 1; i++) {
			maskTemp.append("1");
			if (i % 4 == 0) {
				maskBinaryList.add(maskTemp.toString());
				maskTemp.delete(0, 4);
				continue;
			}
			if (i == prefixLength) {
				if (resident != 0) {
					char[] repeat = new char[4 - resident];
					Arrays.fill(repeat, '0');
					maskTemp.append(new String(repeat));
					maskBinaryList.add(maskTemp.toString());
				}
			}
		}

		/* Convert to Hexadecimal */
		resident = maskBinaryList.size() % 4;
		for (int i = 1; i < maskBinaryList.size() + 1; i++) {
			int decimal = Integer.parseInt(maskBinaryList.get(i - 1), 2);
			if (i % 4 == 0) {
				maskHexadecimal.append(Integer.toString(decimal, 16)).append(":");
			} else {
				maskHexadecimal.append(Integer.toString(decimal, 16));
			}
			if (i == maskBinaryList.size()) {
				if (resident != 0) {
					char[] repeat = new char[4 - resident];
					Arrays.fill(repeat, '0');
					maskHexadecimal.append(new String(repeat));
				}
			}
		}
		return maskHexadecimal.append(":").toString();
	}

	/** Get interface address
	 * @param type IPV6 or IPV4
	 * @return interface address
	 */
	private InterfaceAddress getInterfaceByType(String type) {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
				NetworkInterface anInterface = en.nextElement();
				for (InterfaceAddress interfaceAddress : anInterface.getInterfaceAddresses()) {
					if ("IPV6".equals(type) && interfaceAddress.getAddress() instanceof Inet6Address) {
						return interfaceAddress;
					} else if ("IPV4".equals(type) && interfaceAddress.getAddress() instanceof Inet4Address)
						return interfaceAddress;
				}
			}
		} catch (Exception ex) {
			InventoryLog.e(InventoryLog.getMessage(context, CommonErrorType.NETWORKS_LOCAL_IPV6, ex.getMessage()));
		}
		return null;
	}

	/**
	 * Get the Subnet IpV6
	 * @return string Subnet IpV6
	 */
	public String getSubnetIpV6() {
		String value = "N/A";
		String addressIpV6 = getAddressIpV6();
		if (addressIpV6.contains("::")) {
			value = addressIpV6.split("::", 2)[0] + "::";
		}
		return value;
	}
}
