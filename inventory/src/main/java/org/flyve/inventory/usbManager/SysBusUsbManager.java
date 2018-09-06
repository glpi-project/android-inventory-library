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
        this.myUsbDevices = new HashMap();
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
