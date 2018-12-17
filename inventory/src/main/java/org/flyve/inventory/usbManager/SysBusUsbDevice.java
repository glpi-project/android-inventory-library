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

package  org.flyve.inventory.usbManager;

import java.io.Serializable;

public final class SysBusUsbDevice implements Serializable {
    private final String busNumber;
    private final String deviceNumber;
    private final String devicePath;
    private final String deviceProtocol;
    private final String deviceSubClass;
    private final String maxPower;
    private final String pid;
    private final String reportedProductName;
    private final String reportedVendorName;
    private final String serialNumber;
    private final String serviceClass;
    private final String speed;
    private final String usbVersion;
    private final String vid;

    public static final class Builder {
        private String busNumber;
        private String deviceNumber;
        private String devicePath;
        private String deviceProtocol;
        private String deviceSubClass;
        private String maxPower;
        private String pid;
        private String reportedProductName;
        private String reportedVendorName;
        private String serialNumber;
        private String serviceClass;
        private String speed;
        private String usbVersion;
        private String vid;

        
        public Builder withVid(String val) {
            this.vid = val;
            return this;
        }

        
        public Builder withPid(String val) {
            this.pid = val;
            return this;
        }

        
        public Builder withReportedProductName(String val) {
            this.reportedProductName = val;
            return this;
        }

        
        public Builder withReportedVendorName(String val) {
            this.reportedVendorName = val;
            return this;
        }

        
        public Builder withSerialNumber(String val) {
            this.serialNumber = val;
            return this;
        }

        
        public Builder withSpeed(String val) {
            this.speed = val;
            return this;
        }

        
        public Builder withServiceClass(String val) {
            this.serviceClass = val;
            return this;
        }

        
        public Builder withDeviceProtocol(String val) {
            this.deviceProtocol = val;
            return this;
        }

        
        public Builder withMaxPower(String val) {
            this.maxPower = val;
            return this;
        }

        
        public Builder withDeviceSubClass(String val) {
            this.deviceSubClass = val;
            return this;
        }

        
        public Builder withBusNumber(String val) {
            this.busNumber = val;
            return this;
        }

        
        public Builder withDeviceNumber(String val) {
            this.deviceNumber = val;
            return this;
        }

        
        public Builder withUsbVersion(String val) {
            this.usbVersion = val;
            return this;
        }

        
        public Builder withDevicePath(String val) {
            this.devicePath = val;
            return this;
        }

        
        public SysBusUsbDevice build() {
            return new SysBusUsbDevice(this);
        }
    }

    private SysBusUsbDevice(Builder builder) {
        this.vid = builder.vid;
        this.pid = builder.pid;
        this.reportedProductName = builder.reportedProductName;
        this.reportedVendorName = builder.reportedVendorName;
        this.serialNumber = builder.serialNumber;
        this.speed = builder.speed;
        this.serviceClass = builder.serviceClass;
        this.deviceProtocol = builder.deviceProtocol;
        this.maxPower = builder.maxPower;
        this.deviceSubClass = builder.deviceSubClass;
        this.busNumber = builder.busNumber;
        this.deviceNumber = builder.deviceNumber;
        this.usbVersion = builder.usbVersion;
        this.devicePath = builder.devicePath;
    }

    public String getBusNumber() {
        return this.busNumber;
    }

    public String getServiceClass() {
        return this.serviceClass;
    }

    public String getDeviceNumber() {
        return this.deviceNumber;
    }

    public String getDevicePath() {
        return this.devicePath;
    }

    public String getDeviceProtocol() {
        return this.deviceProtocol;
    }

    public String getDeviceSubClass() {
        return this.deviceSubClass;
    }

    public String getMaxPower() {
        return this.maxPower;
    }

    public String getPid() {
        return this.pid;
    }

    public String getReportedProductName() {
        return this.reportedProductName;
    }

    public String getReportedVendorName() {
        return this.reportedVendorName;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getSpeed() {
        return this.speed;
    }

    public String getUsbVersion() {
        return this.usbVersion;
    }

    public String getVid() {
        return this.vid;
    }
}
