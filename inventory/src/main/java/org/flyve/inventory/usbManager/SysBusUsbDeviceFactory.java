package  org.flyve.inventory.usbManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class SysBusUsbDeviceFactory {

    private static class ContentsReader {
        private final String basePath;

        ContentsReader(String basePath) {
            this.basePath = basePath;
        }

        private String read(UsbProperty property) {
            IOException e;
            FileNotFoundException e2;
            Throwable th;
            File file = new File(this.basePath + property.getFileName());
            StringBuilder fileContents = new StringBuilder(1000);
            if (file.exists() && !file.isDirectory()) {
                BufferedReader reader = null;
                try {
                    BufferedReader reader2 = new BufferedReader(new FileReader(file));
                    try {
                        char[] buf = new char[1024];
                        while (true) {
                            int numRead = reader2.read(buf);
                            if (numRead == -1) {
                                break;
                            }
                            fileContents.append(String.valueOf(buf, 0, numRead));
                            buf = new char[1024];
                        }
                        if (reader2 != null) {
                            try {
                                reader2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                    } catch (FileNotFoundException e4) {
                        e2 = e4;
                        reader = reader2;
                        try {
                            fileContents.setLength(0);
                            e2.printStackTrace();
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e32) {
                                    e32.printStackTrace();
                                }
                            }
                            return fileContents.toString().trim();
                        } catch (Throwable th2) {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e322) {
                                    e322.printStackTrace();
                                }
                            }
                            throw th2;
                        }
                    } catch (IOException e5) {
                        reader = reader2;
                        fileContents.setLength(0);
                        e5.printStackTrace();
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e3222) {
                                e3222.printStackTrace();
                            }
                        }
                        return fileContents.toString().trim();
                    } catch (Throwable th3) {
                        reader = reader2;
                        if (reader != null) {
                            reader.close();
                        }
                        throw th3;
                    }
                } catch (FileNotFoundException e6) {
                    e2 = e6;
                    fileContents.setLength(0);
                    e2.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    return fileContents.toString().trim();
                } catch (IOException e7) {
                    fileContents.setLength(0);
                    e7.printStackTrace();
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    return fileContents.toString().trim();
                }
            }
            return fileContents.toString().trim();
        }
    }

    SysBusUsbDeviceFactory() {
    }

    public SysBusUsbDevice create(File usbDeviceDir) {
        String devicePath = usbDeviceDir.getAbsolutePath() + File.separator;
        ContentsReader reader = new ContentsReader(devicePath);
        String busNumber = reader.read(UsbProperty.BUS_NUMBER);
        String deviceNumber = reader.read(UsbProperty.DEVICE_NUMBER);
        if (busNumber.isEmpty() || deviceNumber.isEmpty()) {
            return null;
        }
        return new SysBusUsbDevice.Builder().withDevicePath(devicePath)
                .withBusNumber(busNumber)
                .withDeviceNumber(deviceNumber)
                .withServiceClass(reader.read(UsbProperty.DEVICE_CLASS))
                .withDeviceProtocol(reader.read(UsbProperty.DEVICE_PROTOCOL))
                .withDeviceSubClass(reader.read(UsbProperty.DEVICE_SUBCLASS))
                .withMaxPower(reader.read(UsbProperty.MAX_POWER))
                .withPid(reader.read(UsbProperty.PID))
                .withReportedProductName(reader.read(UsbProperty.PRODUCT))
                .withReportedVendorName(reader.read(UsbProperty.MANUFACTURER))
                .withSerialNumber(reader.read(UsbProperty.SERIAL))
                .withSpeed(reader.read(UsbProperty.SPEED))
                .withVid(reader.read(UsbProperty.VID))
                .withUsbVersion(reader.read(UsbProperty.VERSION))
                .build();
    }
}
