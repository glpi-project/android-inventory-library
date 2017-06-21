# Inventory Library for Android

![Flyve MDM banner](https://user-images.githubusercontent.com/663460/26935464-54267e9c-4c6c-11e7-86df-8cfa6658133e.png)

[![License](https://img.shields.io/github/license/flyve-mdm/flyve-mdm-android-inventory.svg?&label=License)](https://github.com/flyve-mdm/flyve-mdm-android-inventory/blob/master/LICENSE.md)
[![Follow twitter](https://img.shields.io/twitter/follow/FlyveMDM.svg?style=social&label=Twitter&style=flat-square)](https://twitter.com/FlyveMDM)
[![Telegram Group](https://img.shields.io/badge/Telegram-Group-blue.svg)](https://t.me/flyvemdm)
[![Conventional Commits](https://img.shields.io/badge/Conventional%20Commits-1.0.0-yellow.svg)](https://conventionalcommits.org)
[![GitHub release](https://img.shields.io/github/release/flyve-mdm/flyve-mdm-android-inventory.svg)](https://github.com/flyve-mdm/flyve-mdm-android-inventory/releases)
[![Maven Central](https://img.shields.io/maven-central/v/com.flyvemdm/inventory.svg)](https://bintray.com/flyve-mdm/inventory/flyve-mdm-android-inventory/)

Flyve MDM is a Mobile device management software that enables you to secure and manage all the mobile devices of your business or family via a web-based console.

To get started, check out <https://flyve-mdm.com/>!

## Table of contents

* [Synopsis](#synopsis)
* [Build Status](#build-status)
* [Installation](#installation)
* [Code Example](#code-example)
* [Documentation](#documentation)
* [Contributors](#contributors)
* [Copying](#copying)

## Synopsis

This library help you to create a complete inventory of your Android devices: both hardware and software informations are collected. You get the data about processor, memory, drives, sensors, etc. and also the list and description of installed application on any devices in a beautifull XML as protocol compatible with FusionInventory for GLPI.

You can find more information here:
<http://fusioninventory.org/documentation/dev/spec/protocol/inventory.html>

**What's included?**

* Hardware
* Bios
* Memory
* Inputs
* Sensors
* Drives
* Cpus
* Simcards
* Videos
* Cameras
* Networks
* Envs
* Jvm
* Softwares
* Usb
* Battery

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

## Contributors

* [@rafaelje](https://github.com/rafaelje)
* [@ajsb85](https://github.com/ajsb85)

## Copying

* **Name**: [Flyve MDM](https://flyve-mdm.com/) is a registered trademark of [Teclib'](http://www.teclib-edition.com/en/).
* **Code**: you can redistribute it and/or modify
    it under the terms of the GNU General Public License ([GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html)).
* **Documentation**: released under Attribution 4.0 International ([CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)).