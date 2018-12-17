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

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SysBusUsbManager {
    private final HashMap<String, SysBusUsbDevice> myUsbDevices;
    private final SysBusUsbDeviceFactory sysBusUsbDeviceFactory;
    private final String usbSysPath;
    private final Validation validation;

    public SysBusUsbManager() {
        this("/sys/bus/usb/devices/");
    }

    public SysBusUsbManager(String usbSysPath) {
        this.usbSysPath = usbSysPath;
        this.myUsbDevices = new HashMap<>();
        this.sysBusUsbDeviceFactory = new SysBusUsbDeviceFactory();
        this.validation = new Validation();
    }

    public Map<String, SysBusUsbDevice> getUsbDevices() {
        populateList(this.usbSysPath);
        return Collections.unmodifiableMap(this.myUsbDevices);
    }

    private void populateList(String path) {
        this.myUsbDevices.clear();
        for (File child : this.validation.getListOfChildren(new File(path))) {
            if (this.validation.isValidUsbDeviceCandidate(child)) {
                SysBusUsbDevice usb = this.sysBusUsbDeviceFactory.create(child.getAbsoluteFile());
                if (usb != null) {
                    this.myUsbDevices.put(child.getName(), usb);
                }
            }
        }
    }
}
