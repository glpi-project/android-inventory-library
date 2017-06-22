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

import android.app.Service;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.flyvemdm.inventory.FILog;

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

		WifiManager pWM = (WifiManager) xCtx.getSystemService(Service.WIFI_SERVICE);
		boolean wasWifiEnabled = pWM.isWifiEnabled();

		// Enable Wifi State if not
		if (!wasWifiEnabled) {
			pWM.setWifiEnabled(true);
		}
		Category c = new Category("NETWORKS");
		c.put("TYPE", TYPE);

		dhcp = pWM.getDhcpInfo();
		wifi = pWM.getConnectionInfo();

		FILog.d("<===WIFI DHCP===>");
		FILog.d("dns1=" + StringUtils.intToIp(dhcp.dns1));
		FILog.d("dns2=" + StringUtils.intToIp(dhcp.dns2));
		FILog.d("leaseDuration=" + dhcp.leaseDuration);

		c.put("MACADDR", getMacaddr());
		c.put("SPEED", getSpeed());
		c.put("BSSID", getBSSID());
		c.put("SSID", getSSID());
		c.put("IPGATEWAY", getIpgateway());
		c.put("IPADDRESS", getIpaddress());
		c.put("IPMASK", getIpmask());
		c.put("IPDHCP", getIpdhcp());

		this.add(c);
		// Restore Wifi State
		if (!wasWifiEnabled) {
			pWM.setWifiEnabled(false);
		}
	}

	public String getMacaddr() {
		return wifi.getMacAddress();
	}

	public String getSpeed() {
		return String.valueOf(wifi.getLinkSpeed());
	}

	public String getBSSID() {
		return String.valueOf(wifi.getBSSID());
	}

	public String getSSID() {
		return String.valueOf(wifi.getBSSID());
	}

	public String getIpgateway() {
		return StringUtils.intToIp(dhcp.gateway);
	}

	public String getIpaddress() {
		return StringUtils.intToIp(dhcp.ipAddress);
	}

	public String getIpmask() {
		return StringUtils.intToIp(dhcp.netmask);
	}

	public String getIpdhcp() {
		return StringUtils.intToIp(dhcp.serverAddress);
	}
}
