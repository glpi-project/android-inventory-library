# Inventory Library for Android

![GLPI Android Inventory banner](https://github.com/glpi-project/android-inventory-library/assets/7335054/7d82c661-bb74-40dd-ac1f-8f42f0e8542b)

[![License](https://img.shields.io/github/license/glpi-project/android-inventory-library.svg?&label=License)](https://github.com/glpi-project/android-inventory-library/blob/develop/LICENSE.md)
[![License: CC BY 4.0](https://img.shields.io/badge/License-CC%20BY%204.0-lightgrey.svg)](https://creativecommons.org/licenses/by/4.0/)
[![Follow twitter](https://img.shields.io/twitter/follow/Teclib.svg?style=social&label=Twitter&style=flat-square)](https://twitter.com/teclib)
[![Telegram Group](https://img.shields.io/badge/Telegram-Group-blue.svg)](https://t.me/glpien)
[![Project Status: Active](http://www.repostatus.org/badges/latest/active.svg)](http://www.repostatus.org/#active)
[![Conventional Commits](https://img.shields.io/badge/Conventional%20Commits-1.0.0-yellow.svg)](https://conventionalcommits.org)
[![Greenkeeper badge](https://img.shields.io/badge/Greenkeeper-enabled-4c1.svg?colorA=555&style=flat)](https://greenkeeper.io)
[![GitHub release](https://img.shields.io/github/release/glpi-project/android-inventory-library.svg)](https://github.com/glpi-project/android-inventory-library/releases)
[![GitHub build](https://img.shields.io/circleci/build/github/glpi-project/android-inventory-library.svg)](https://circleci.com/gh/glpi-project/android-inventory-library/)

GLPI Android Inventory Library is an android inventory library written in Java

## Table of contents
* [Synopsis](#synopsis)
* [Build Status](#build-status)
* [Installation](#installation)
* [Code Example](#code-example)
* [Documentation](#documentation)
* [Versioning](#versioning)
* [Contribute](#contribute)
* [Contact](#contact)
* [Professional Services](#professional-services)
* [Copying](#copying)

## Synopsis

The Inventory Library for Android collects a complete inventory of your Android devices. It allows you to export your inventory in a beautiful XML or JSON as protocol compatible GLPI Native Inventory.

You can find more information about the GLPI Native Inventory Protocol here:
<https://github.com/glpi-project/inventory_format>

### Data collected

- Account Info
- Accesslog
- Hardware
- User
- Storages
- Operating System
- BIOS
- Memories
- Inputs
- Sensors
- Drives
- CPUs
- Videos
- Cameras
- Networks
- Environments variables
- JVMS
- Softwares
- USB
- Battery
- Controllers

## Installation

Download the latest JAR or AAR and include it in your Android project as an external library



## Code Example

It's easy to implement in your code, as you can see in the following examples

### Java

```java
InventoryTask inventoryTask = new InventoryTask(MainActivity.this, "Agent_v1.0", new InventoryTask.OnTaskCompleted() {
  @Override
  public void onTaskCompleted(String data) {
    Log.d("XML", data);
  }
});

inventoryTask.execute();
```

### Kotlin

```kotlin
val inventoryTask = InventoryTask(this@MainActivity, "Agent_v1.0", object : InventoryTask.OnTaskCompleted() {
    override fun onTaskCompleted(data: String) {
        Log.d("XML", data)
    }
})

inventoryTask.execute()
```

## Versioning

In order to provide transparency on our release cycle and to maintain backward compatibility, GLPI Android Inventory Library is maintained under [the Semantic Versioning guidelines](http://semver.org/). We are committed to following and complying with the rules, the best we can.

See [the tags section of our GitHub project](https://github.com/glpi-project/android-inventory-library/tags) for changelogs for each release version of Flyve MDM. Release announcement posts on [the official Teclib' blog](http://www.teclib-edition.com/en/communities/blog-posts/) contain summaries of the most noteworthy changes made in each release.

## Contribute

Want to file a bug, contribute some code, or improve documentation? Excellent! Read up on our
guidelines for [contributing](./CONTRIBUTING.md) and then check out one of our issues in the [Issues Dashboard](https://github.com/glpi-project/android-inventory-library/issues).

## GLPI Network - Professional support

You want to report a bug (and get a fix quickly) or do you need a guarantee for Android Inventory Library?

You can subscribe to our professional support GLPI Network [here](https://services.glpi-network.com).

This subscription includes a guarantee through a service level contract between your company and our team in charge of GLPI development (core, supported plugins and GLPI Agent) as well as exclusive features and services.

## Copying

* **Name**: [Flyve MDM](https://flyve-mdm.com/) is a registered trademark of [Teclib'](http://www.teclib-edition.com/en/).
* **Code**: you can redistribute it and/or modify
    it under the terms of the GNU General Public License ([GPLv3](https://www.gnu.org/licenses/gpl-3.0.en.html)).
* **Documentation**: released under Attribution 4.0 International ([CC BY 4.0](https://creativecommons.org/licenses/by/4.0/)).
