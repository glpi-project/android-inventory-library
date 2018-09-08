package  org.flyve.inventory.usbManager;

import java.io.File;

class Validation {
    Validation() {
    }

    public boolean isValidUsbDeviceCandidate(File file) {
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        if (".".equals(file.getName()) || "..".equals(file.getName())) {
            return false;
        }
        return true;
    }

    public File[] getListOfChildren( File path) {
        if (path.exists() && path.isDirectory() && path.listFiles() != null) {
            return path.listFiles();
        }
        return new File[0];
    }
}
