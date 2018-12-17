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

public enum UsbProperty {
    PID("idProduct"),
    VID("idVendor"),
    MANUFACTURER("manufacturer"),
    PRODUCT("product"),
    VERSION("version"),
    DEVICE_CLASS("bDeviceClass"),
    DEVICE_SUBCLASS("bDeviceSubClass"),
    DEVICE_NUMBER("devnum"),
    DEVICE_PROTOCOL("bDeviceProtocol"),
    MAX_POWER("bMaxPower"),
    BUS_NUMBER("busnum"),
    SERIAL("serial"),
    SPEED("speed"),
    SUPPORTS_AUTOSUSPEND("supports_autosuspend"),
    AUTHORIZED("authorized"),
    MODALIAS("modalias"),
    ALTERNATIVE_SETTING("bAlternateSetting"),
    NUM_INTERFACES("bNumInterfaces"),
    NUM_ENDPOINTS("bNumEndpoints"),
    INTERFACE("interface"),
    INTERFACE_CLASS("bInterfaceClass"),
    INTERFACE_NUMBER("bInterfaceNumber"),
    INTERFACE_PROTOCOL("bInterfaceProtocol"),
    INTERFACE_SUBCLASS("bInterfaceSubClass");
    
    private final String fileName;

    private UsbProperty(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
