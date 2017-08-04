---
layout: page
---

# Synopsis

Flyve MDM is a Mobile device management software that enables you to secure and manage all the mobile devices of your business or family via a web-based console.

To get started, check out <https://flyve-mdm.com/>!

This library help you to create a complete inventory of your Android devices: both hardware and software informations are collected. You get the data about processor, memory, drives, sensors, etc. and also the list and description of installed application on any devices in a beautifull XML as protocol compatible with FusionInventory for GLPI.

You can find more information here:
<http://fusioninventory.org/documentation/dev/spec/protocol/inventory.html>

### **What's included?**

 <ol class="list-items">
            <!-- USB -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-usb">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>USB</strong>
              </div>
              <div class="collapse" id="list-item-usb">
                <div class="row">
                  <div class="col-md-4">
                    Class
                  </div>
                  <div class="col-md-4">
                    Product ID
                  </div>
                  <div class="col-md-4">
                    Vendor ID
                  </div>
                  <div class="col-md-4">
                    Subclass
                  </div>
                </div>
              </div>
            </li>
            <!-- Sensors -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-sensors">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Sensors</strong>
              </div>
              <div class="collapse" id="list-item-sensors">
                <div class="row">
                  <div class="col-md-4">
                    Name
                  </div>
                  <div class="col-md-4">
                    Manufacturer
                  </div>
                  <div class="col-md-4">
                    Type
                  </div>
                  <div class="col-md-4">
                    Power
                  </div>
                  <div class="col-md-4">
                    Version
                  </div>
                </div>
              </div>
            </li>
            <!-- Software -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-software">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Software</strong>
              </div>
              <div class="collapse" id="list-item-software">
                <div class="row">
                  <div class="col-md-4">
                    Name
                  </div>
                  <div class="col-md-4">
                    Version
                  </div>
                  <div class="col-md-4">
                    Filesize
                  </div>
                  <div class="col-md-4">
                    From
                  </div>
                </div>
              </div>
            </li>
            <!-- Memory -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-memory">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Memories</strong>
              </div>
              <div class="collapse" id="list-item-memory">
                <div class="row">
                  <div class="col-md-4">
                    Description
                  </div>
                  <div class="col-md-4">
                    Capacity
                  </div>
                </div>
              </div>
            </li>
            <!-- Cameras -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-camera">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Cameras</strong>
              </div>
              <div class="collapse" id="list-item-camera">
                <div class="row">
                  <div class="col-md-4">
                    Resolutions
                  </div>
                </div>
              </div>
            </li>
            <!-- Networks -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-networks">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Networks</strong>
              </div>
              <div class="collapse" id="list-item-networks">
                <div class="row">
                  <div class="col-md-4">
                    Type
                  </div>
                  <div class="col-md-4">
                    Speed
                  </div>
                  <div class="col-md-4">
                    BSSID
                  </div>
                  <div class="col-md-4">
                    SSID
                  </div>
                  <div class="col-md-4">
                    IP Gateway
                  </div>
                  <div class="col-md-4">
                    IP Address
                  </div>
                  <div class="col-md-4">
                    IP Mask
                  </div>
                  <div class="col-md-4">
                    IP DHCP
                  </div>
                </div>
              </div>
            </li>
            <!-- Battery -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-battery">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Battery</strong>
              </div>
              <div class="collapse" id="list-item-battery">
                <div class="row">
                  <div class="col-md-4">
                    Chemistry
                  </div>
                  <div class="col-md-4">
                    Temperature
                  </div>
                  <div class="col-md-4">
                    Voltage
                  </div>
                  <div class="col-md-4">
                    Level
                  </div>
                  <div class="col-md-4">
                    Health
                  </div>
                  <div class="col-md-4">
                    Status
                  </div>
                </div>
              </div>
            </li>
            <!-- CPUs -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-cpu">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>CPUs</strong>
              </div>
              <div class="collapse" id="list-item-cpu">
                <div class="row">
                  <div class="col-md-4">
                    Name
                  </div>
                </div>
              </div>
            </li>
            <!-- BIOS -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-bios">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>BIOS</strong>
              </div>
              <div class="collapse" id="list-item-bios">
                <div class="row">
                  <div class="col-md-4">
                    BIOS release date
                  </div>
                  <div class="col-md-4">
                    System Model
                  </div>
                  <div class="col-md-4">
                    SSN
                  </div>
                </div>
              </div>
            </li>
            <!-- Inputs -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-inputs">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Inputs</strong>
              </div>
              <div class="collapse" id="list-item-inputs">
                <div class="row">
                  <div class="col-md-4">
                    Keyboard
                  </div>
                  <div class="col-md-4">
                    Touchscreen
                  </div>
                </div>
              </div>
            </li>
            <!-- Drives -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-drives">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Drives</strong>
              </div>
              <div class="collapse" id="list-item-drives">
                <div class="row">
                  <div class="col-md-4">
                    Volumn
                  </div>
                  <div class="col-md-4">
                    Total 
                  </div>
                  <div class="col-md-4">
                    Free
                  </div>
                </div>
              </div>
            </li>
            <!-- Accesslog -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-drives">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Accesslog</strong>
              </div>
              <div class="collapse" id="list-item-drives">
                <div class="row">
                  <div class="col-md-4">
                    Log date 
                  </div>
                  <div class="col-md-4">
                    User ID 
                  </div>
                </div>
              </div>
            </li>
            <!-- SIM Cards -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-simcards">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>SIM Cards</strong>
              </div>
              <div class="collapse" id="list-item-simcards">
                <div class="row">
                  <div class="col-md-4">
                    Country
                  </div>
                  <div class="col-md-4">
                    Operator code
                  </div>
                  <div class="col-md-4">
                    Operator Name
                  </div>
                  <div class="col-md-4">
                    Serial SERIAL
                  </div>
                  <div class="col-md-4">
                    State
                  </div>
                  <div class="col-md-4">
                    Line number
                  </div>
                  <div class="col-md-4">
                    Subscriber ID
                  </div>
                </div>
              </div>
            </li>
            <!-- Environments vars -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-envs">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Environments variables</strong>
              </div>
              <div class="collapse" id="list-item-envs">
                <div class="row">
                  <div class="col-md-4">
                    Key
                  </div>
                  <div class="col-md-4">
                    Value
                  </div>
                </div>
              </div>
            </li>
            <!-- JVM -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-jvm">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>JVM</strong>
              </div>
              <div class="collapse" id="list-item-jvm">
                <div class="row">
                  <div class="col-md-4">
                    Name
                  </div>
                  <div class="col-md-4">
                    Language
                  </div>
                  <div class="col-md-4">
                    Vendor
                  </div>
                  <div class="col-md-4">
                    Runtime
                  </div>
                  <div class="col-md-4">
                    Home
                  </div>
                  <div class="col-md-4">
                    Version
                  </div>
                  <div class="col-md-4">
                    Classpath
                  </div>
                </div>
              </div>
            </li>
            <!-- Videos -->
            <li class="list-items-row">
              <div class="row" data-toggle="collapse" aria-expanded="false" data-target="#list-item-videos">
                <i class="glyph glyph-add"></i>
                <i class="glyph glyph-remove"></i>
                <strong>Videos</strong>
              </div>
              <div class="collapse" id="list-item-videos">
                <div class="row">
                  <div class="col-md-4">
                    Resolution
                  </div>
                </div>
              </div>
            </li>
          </ol>

## Build Status

| **Release channel** | **Beta channel** |
|:---:|:---:|
| [![Build Status](https://travis-ci.org/flyve-mdm/flyve-mdm-android-inventory.svg?branch=master)](https://travis-ci.org/flyve-mdm/flyve-mdm-android-inventory) | [![Build Status](https://travis-ci.org/flyve-mdm/flyve-mdm-android-inventory.svg?branch=develop)](https://travis-ci.org/flyve-mdm/flyve-mdm-android-inventory) |

## Installation

Download the latest JAR, grab via Maven or insert on `build.gradle` at app level.

### Maven

```xml
<dependency>
  <groupId>com.flyvemdm</groupId>
  <artifactId>inventory</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### Gradle

```groovy
compile 'com.flyvemdm:inventory:1.0.0'
```

## Code Example

It's easy to implement in your code

```java
InventoryTask inventoryTask = new InventoryTask(MainActivity.this, "Agent_v1.0", new InventoryTask.OnTaskCompleted() {
  @Override
  public void onTaskCompleted(String data) {
    Log.d("XML", data);
  }
});

inventoryTask.execute();
```

## Documentation

We share long-form content about the project in the [wiki](https://github.com/flyve-mdm/flyve-mdm-android-inventory/wiki).

## Versioning

In order to provide transparency on our release cycle and to maintain backward compatibility, Flyve MDM is maintained under [the Semantic Versioning guidelines](http://semver.org/). We are committed to following and complying with the rules, the best we can.

See [the tags section of our GitHub project](http://github.com/flyve-mdm/flyve-mdm-blackberry-admin-dashboard/tags) for changelogs for each release version of Flyve MDM. Release announcement posts on [the official Teclib' blog](http://www.teclib-edition.com/en/communities/blog-posts/) contain summaries of the most noteworthy changes made in each release.

## Contribute

Want to file a bug, contribute some code, or improve documentation? Excellent! Read up on our
guidelines for [contributing](./CONTRIBUTING.md) and then check out one of our issues in the [Issues Dashboard](https://github.com/flyve-mdm/flyve-mdm-android-inventory/issues).

## Contact

For notices about major changes and general discussion of Flyve MDM development, subscribe to the [/r/FlyveMDM](http://www.reddit.com/r/FlyveMDM) subreddit. 
You can also chat with us via IRC in [#flyve-mdm on freenode](http://webchat.freenode.net/?channels=flyve-mdm]).
Ping me @rafaelje in the IRC chatroom if you get stuck.

## Copying

* **Name**: [Flyve MDM](https://flyve-mdm.com/) is a registered trademark of [Teclib'](http://www.teclib-edition.com/en/).
* **Code**: you can redistribute it and/or modify
    it under the terms of the GNU General Public License ([GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html)).
* **Documentation**: released under Attribution 4.0 International ([CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)).
