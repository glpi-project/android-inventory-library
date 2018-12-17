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

package org.flyve.inventory;

public class CommonErrorType {

    /* Battery */
    public static final int BATTERY = 100;
    public static final int BATTERY_TECHNOLOGY = 101;
    public static final int BATTERY_TEMPERATURE = 102;
    public static final int BATTERY_VOLTAGE = 103;
    public static final int BATTERY_LEVEL = 104;
    public static final int BATTERY_HEALTH = 105;
    public static final int BATTERY_STATUS = 106;
    public static final int BATTERY_CAPACITY = 107;

    /* Bios */
    public static final int BIOS = 200;
    public static final int BIOS_DATE = 201;
    public static final int BIOS_MANUFACTURER = 202;
    public static final int BIOS_VERSION = 203;
    public static final int BIOS_BOARD_MANUFACTURER = 204;
    public static final int BIOS_MOTHER_BOARD_MODEL = 205;
    public static final int BIOS_TAG = 206;
    public static final int BIOS_MOTHER_BOARD_SERIAL = 207;
    public static final int BIOS_SYSTEM_SERIAL = 208;
    public static final int BIOS_SERIAL_PRIVATE = 209;
    public static final int BIOS_CPU_SERIAL = 210;

    /* Bluetooth */
    public static final int BLUETOOTH = 300;
    public static final int BLUETOOTH_HARDWARE_ADDRESS = 301;
    public static final int BLUETOOTH_NAME = 302;

    /* Camera */
    public static final int CAMERA = 400;
    public static final int CAMERA_COUNT = 401;
    public static final int CAMERA_CHARACTERISTICS = 402;
    public static final int CAMERA_RESOLUTION = 403;
    public static final int CAMERA_FACING_STATE = 404;
    public static final int CAMERA_FLASH_UNIT = 405;
    public static final int CAMERA_IMAGE_FORMAT = 406;
    public static final int CAMERA_ORIENTATION = 407;
    public static final int CAMERA_VIDEO_RESOLUTION = 408;
    public static final int CAMERA_FOCAL_LENGTH = 409;
    public static final int CAMERA_SENSOR_SIZE = 410;
    public static final int CAMERA_MANUFACTURER = 411;
    public static final int CAMERA_MODEL = 412;
    public static final int CAMERA_BUFFERED = 413;
    public static final int CAMERA_VALUE_STRING = 414;
    public static final int CAMERA_LIST_BYTES = 415;

    /* Controllers */
    public static final int CONTROLLERS = 500;
    public static final int CONTROLLERS_FILE = 501;
    public static final int CONTROLLERS_DRIVERS = 502;

    /* CPUs */
    public static final int CPU = 600;
    public static final int CPU_CORE = 601;
    public static final int CPU_ARCH = 602;
    public static final int CPU_FAMILY_NAME = 603;
    public static final int CPU_FAMILY_NUMBER = 604;
    public static final int CPU_MANUFACTURER = 605;
    public static final int CPU_MODEL = 606;
    public static final int CPU_NAME = 607;
    public static final int CPU_FREQUENCY = 608;
    public static final int CPU_THREAD = 609;

    /* Drives */
    public static final int DRIVES = 700;
    public static final int DRIVES_VOLUME = 701;
    public static final int DRIVES_TOTAL = 702;
    public static final int DRIVES_FREE_SPACE = 703;
    public static final int DRIVES_FILE_SYSTEM = 704;
    public static final int DRIVES_TYPE = 705;

    /* Hardware */
    public static final int HARDWARE = 800;
    public static final int HARDWARE_DATE_LAST_LOGGED_USER = 801;
    public static final int HARDWARE_LAST_LOGGED_USER = 802;
    public static final int HARDWARE_USER_TAG = 803;
    public static final int HARDWARE_USER_INFO = 804;
    public static final int HARDWARE_NAME = 805;
    public static final int HARDWARE_VERSION = 806;
    public static final int HARDWARE_ARCH_NAME = 807;
    public static final int HARDWARE_UUID = 808;

    /* Inputs */
    public static final int INPUTS = 900;
    public static final int INPUTS_KEY_BOARD = 901;
    public static final int INPUTS_TOUCH_SCREEN = 902;

    /* Jvm */
    public static final int JVM = 1000;
    public static final int JVM_NAME = 1001;
    public static final int JVM_VENDOR = 1002;
    public static final int JVM_LANGUAGE = 1003;
    public static final int JVM_RUNTIME = 1004;
    public static final int JVM_HOME = 1005;
    public static final int JVM_VERSION = 1006;
    public static final int JVM_CLASS_PATH = 1007;

    /* Location Providers */
    public static final int LOCATION = 1100;
    public static final int LOCATION_NAME = 1101;

    /* Memory */
    public static final int MEMORY = 1200;
    public static final int MEMORY_CAPACITY = 1201;
    public static final int MEMORY_RAM_INFO = 1202;
    public static final int MEMORY_SPLIT_RAM_INFO = 1203;
    public static final int MEMORY_RAM_PROP = 1204;
    public static final int MEMORY_TYPE = 1205;
    public static final int MEMORY_SPEED = 1206;

    /* Networks */
    public static final int NETWORKS = 1300;
    public static final int NETWORKS_MAC_ADDRESS = 1301;
    public static final int NETWORKS_MAC_ADDRESS_VALUE = 1302;
    public static final int NETWORKS_SPEED = 1303;
    public static final int NETWORKS_BSS_ID = 1304;
    public static final int NETWORKS_SS_ID = 1305;
    public static final int NETWORKS_IP_GATEWAY = 1306;
    public static final int NETWORKS_IP_ADDRESS = 1307;
    public static final int NETWORKS_IP_MASK = 1308;
    public static final int NETWORKS_IP_DH_CP = 1309;
    public static final int NETWORKS_IP_SUBNET = 1310;
    public static final int NETWORKS_STATUS = 1311;
    public static final int NETWORKS_CAT_INFO = 1312;
    public static final int NETWORKS_DESCRIPTION = 1313;
    public static final int NETWORKS_LOCAL_IPV6 = 1314;

    /* Operating System */
    public static final int OPERATING_SYSTEM = 1400;
    public static final int OPERATING_SYSTEM_BOOT_TIME = 1401;
    public static final int OPERATING_SYSTEM_KERNEL = 1402;
    public static final int OPERATING_SYSTEM_TIME_ZONE = 1403;
    public static final int OPERATING_SYSTEM_CURRENT = 1404;
    public static final int OPERATING_SYSTEM_SSH_KEY = 1405;

    /* Phone Status */
    public static final int PHONE_STATUS = 1500;

    /* SENSORS */
    public static final int SENSORS = 1600;
    public static final int SENSORS_NAME = 1601;
    public static final int SENSORS_MANUFACTURER = 1602;
    public static final int SENSORS_TYPE = 1603;
    public static final int SENSORS_POWER = 1604;
    public static final int SENSORS_VERSION = 1605;

    /* SIM CARDS */
    public static final int SIM_CARDS = 1700;
    public static final int SIM_CARDS_MULTIPLE = 1701;
    public static final int SIM_CARDS_STATE_BY_SLOT = 1702;
    public static final int SIM_CARDS_COUNTRY = 1703;
    public static final int SIM_CARDS_OPERATOR_CODE = 1704;
    public static final int SIM_CARDS_OPERATOR_NAME = 1705;
    public static final int SIM_CARDS_SERIAL = 1706;
    public static final int SIM_CARDS_STATE = 1707;
    public static final int SIM_CARDS_LINE_NUMBER = 1708;
    public static final int SIM_CARDS_SUBSCRIBER_ID = 1709;

    /* Software */
    public static final int SOFTWARE = 1800;
    public static final int SOFTWARE_NAME = 1801;
    public static final int SOFTWARE_PACKAGE = 1802;
    public static final int SOFTWARE_INSTALL_DATE = 1803;
    public static final int SOFTWARE_VERSION = 1804;
    public static final int SOFTWARE_FILE_SIZE = 1805;
    public static final int SOFTWARE_FOLDER = 1806;
    public static final int SOFTWARE_REMOVABLE = 1807;
    public static final int SOFTWARE_USER_ID = 1808;

    /* Storage */
    public static final int STORAGE = 1900;
    public static final int STORAGE_PARTITION = 1901;
    public static final int STORAGE_VALUES = 1902;

    /* Usb */
    public static final int USB = 2000;
    public static final int USB_SYS_BUS = 2001;
    public static final int USB_SERVICE = 2002;
    public static final int USB_PID = 2003;
    public static final int USB_VID = 2004;
    public static final int USB_DEVICE_SUB_CLASS = 2005;
    public static final int USB_REPORTED_PRODUCT_NAME = 2006;
    public static final int USB_USB_VERSION = 2007;
    public static final int USB_SERIAL_NUMBER = 2008;

    /* User */
    public static final int USER = 2100;
    public static final int USER_NAME = 2101;

    /* Videos */
    public static final int VIDEOS = 2200;
    public static final int VIDEOS_RESOLUTION = 2201;

    /* Categories */
    public static final int CATEGORY_TO_XML = 2300;
    public static final int CATEGORY_TO_XML_WITHOUT_PRIVATE = 2301;
    public static final int CATEGORY_TO_JSON = 2302;
    public static final int CATEGORY_TO_JSON_WITHOUT_PRIVATE = 2303;
    public static final int CATEGORIES_TO_XML = 2304;
    public static final int CATEGORIES_TO_XML_WITHOUT_PRIVATE = 2305;
    public static final int CATEGORIES_TO_JSON = 2306;
    public static final int CATEGORIES_TO_JSON_WITHOUT_PRIVATE = 2307;

    /* Utils */
    public static final int UTILS_CAT_INFO = 2400;
    public static final int UTILS_CAT_INFO_MULTIPLE = 2401;
    public static final int UTILS_CREATE_XML = 2402;
    public static final int UTILS_CREATE_JSON = 2403;
    public static final int UTILS_DEVICE_PROPERTIES = 2404;
    public static final int UTILS_LOAD_JSON_ASSET = 2405;

    /* Modems */
    public static final int MODEMS = 2500;
    public static final int MODEMS_IMEI = 2501;
}