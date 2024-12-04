# CHANGELOG


## [UNRELEASED]


### Bug Fixes

- **OperatingSystem:** correct `<OFFSET>` to use 4 digits
- **User:** prevent error with `android.permission.QUERY_USERS` (only for sys app)
- **Repo:** update `CHANGELOG.md`.
- **Repo:** update `README.md`.
- **Build:** remove useless `composer`.
- **Build:** remove useless dependencies.
- **Repo:** update `CONTRIBUTING.md`.
- **Build:** remove useless `gem` dependencies.
- **Repo:** update `package.json`.

### Features

- **Core:** Migrate to `SDK` 34
- **Core:** Min `SDK` set to 24
- **Test:** Migrate test to `androidx.test:runner`
- **Core:** Bump `gradle` to 8.1.1
- **Core:** Bump `kotlin` to 1.6.0
- **Build:** update GitHub Actions.


## [1.6.0](https://github.com/glpi-project/android-inventory-library/compare/1.5.0..1.6.0) (2023-08-31)

### Bug Fixes
- **Build:** GithubAction only for PullRequest.
- **Build:** clean release note.
- **Build:** fix release GithubAction.
- **Build:** fix release script.
- **Build:** fix release template.
- **Build:** fix YAML indent.
- **Build:** optimize script.
- **Build:** prevent Type mismatch: inferred type is String? but String was expected on build.
- **Build:** remove bintray scripts.
- **Build:** remove capability to bypass CI.
- **Build:** update some packages.
- **Build:** yarn upgrade.
- **Inventory:** display full Android version.
- **Inventory:** fix Android Q storage path.
- **Inventory:** fix Android Q storage permission.
- **Lib:** skip software if name is empty.
- **Lib:** skip software if name is not available.
- **Readme:** fix README.
- **Release:** fix generated Changelog.
- **Test:** fix method arguments.
- **XML:** remove all CDATA nodes.

### Features
- **Build:** Github Action for release.
- **Build:** skip CI from specific label.
- **Build:** clean useless CI scripts.
- **Build:** generate Changelog from ConventionalCommit.
- **Core:** add new option for asset item type.
- **Core:** bump Compile/Target SdkVersion to 30.

---

## [1.5.0](https://github.com/glpi-project/android-inventory-library/compare/1.4.4..1.5.0) (2021-08-13)

### Bug Fixes
- **Build:** disable documentation step.
- **Build:** fix Javadoc generation.
- **Inventory:** always set designation.
- **Inventory:** catch error when getting `IMEI`.

### Features
- **Inventory:** improve camera process.
- **Inventory:** native inventory compatibility.
- **Inventory:** retrieve more information from camera.

---

## [1.4.4](https://github.com/glpi-project/android-inventory-library/compare/1.4.3..1.4.4) (2019-12-30)

### Bug Fixes
- **Inventory:** add data to `CDATA` node.

---

## [1.4.3](https://github.com/glpi-project/android-inventory-library/compare/1.4.2..1.4.3) (2019-12-19)

### Bug Fixes
- **Bios:** set the accessible flag for this object.
- **Build:** fix dependencies.
- **Core:** add support for HTTP.
- **Doc:** fix word typo in documentation.
- **Hardware:** correctly detect empty hardware name value.
- **Network:** correctly detect network description.

---

## [1.4.2](https://github.com/glpi-project/android-inventory-library/compare/1.4.1..1.4.2) (2019-12-18)

### Bug Fixes
- **Core:** request permission for modem information.
- **Inventory:** improve `IMEI` inventory process.

---

## [1.4.1](https://github.com/glpi-project/android-inventory-library/compare/1.4.0..1.4.1) (2019-12-13)

### Bug Fixes
- **Inventory:** fix anonymous inventory.
- **Log:** improve logging.

---

## [1.4.0](https://github.com/glpi-project/android-inventory-library/compare/1.3.6..1.4.0) (2019-12-04)

### Bug Fixes
- **Inventory:** add all node values to `CDATA` format.
- **Inventory:** fix inventory `XML` / `JSON` data.
- **Inventory:** return empty rather than `N/A`.

### Features
- **Build:** add instrumentation tests.

---

## [1.3.6](https://github.com/glpi-project/android-inventory-library/compare/1.3.5..1.3.6) (2019-11-18)

### Bug Fixes
- **Inventory:** better test.
- **Inventory:** return `UUID` if needed.

---

## [1.3.5](https://github.com/glpi-project/android-inventory-library/compare/1.3.4..1.3.5) (2019-11-18)

### Bug Fixes
- **Build:** fix GitHub release.

---

## [1.3.4](https://github.com/glpi-project/android-inventory-library/compare/1.3.3..1.3.4) (2019-11-18)

### Bug Fixes
- **Build:** fix GitHub release.

---

## [1.3.3](https://github.com/glpi-project/android-inventory-library/compare/1.3.2..1.3.3) (2019-11-18)

### Bug Fixes
- **Build:** force push to develop.

---

## [1.3.2](https://github.com/glpi-project/android-inventory-library/compare/1.3.1..1.3.2) (2019-11-18)

### Bug Fixes
- **Yarn:** update `yarn.lock`.

---

## [1.3.1](https://github.com/glpi-project/android-inventory-library/compare/1.3.0..1.3.1) (2019-11-18)

### Bug Fixes
- **Inventory:** improve code.

---

## [1.3.0](https://github.com/glpi-project/android-inventory-library/compare/1.2.0..1.3.0) (2019-11-08)

### Bug Fixes
- **Build:** fix undefined environment variable.
- **Inventory:** clean `UUID`.
- **Inventory:** get model name if hostname returns an empty value.
- **Share-Inventory:** use file provider for URI.

### Features
- **Build:** add `javadoc.jar` and `source.jar` generation.
- **Core:** bump version.

---

## [1.2.0](https://github.com/glpi-project/android-inventory-library/compare/1.1.0..1.2.0) (2019-11-08)

### Bug Fixes
- **Inventory:** get model name if hostname returns an empty value.
- **Share-Inventory:** use file provider for URI.

### Features
- **Build:** add `javadoc.jar` and `source.jar` generation.

---

## [1.1.0](https://github.com/glpi-project/android-inventory-library/compare/..1.1.0) (2019-11-07)

### Bug Fixes
- **Sensor:** return "unknown" instead of an empty value in the type function.
- **Battery:** manage exceptions.
- **Battery:** update battery information retrieval.
- **BIOS:** added support to get SN in Android < 7.
- **BIOS:** manage exceptions.
- **Bluetooth:** manage exceptions.
- **Build:** clean uncommitted changes.
- **Camera:** add `lintOptions` in Gradle.
- **Camera:** manage exceptions.
- **Camera:** remove invalid characters.
- **Camera:** validate list size.
- **Category:** change capture log and Gradle `minSdk`.
- **Category:** log errors.
- **Category:** remove special characters from XML output.
- **Character:** remove `&` from XML output.
- **CI:** fix Javadoc generation.
- **Core:** fix permission check.
- **CPUs:** manage exceptions.
- **Date:** convert timestamp to date format.
- **DeviceId:** change output XML and JSON to include `DeviceId`.
- **DeviceId:** validate exception handling.
- **Drives:** add information about external removable storage.
- **Drives:** manage exceptions.
- **Envs:** manage exceptions.
- **File:** set `append` to `false` in file writer.
- **Gradle:** remove `gnag`.
- **Hardware:** add last logged user information.
- **Hardware:** manage exceptions.
- **Hardware:** validate when no user info is found.
- **IMEI:** handle exceptions for IMEI retrieval.
- **Input:** update input information.
- **Inputs:** manage exceptions.
- **Inventory:** add `getCacheFilePath` method.
- **Inventory:** check permissions to prevent `SecurityException`.
- **JVM:** manage exceptions.
- **Kernel:** remove error messages from kernel version retrieval.
- **Location:** manage exceptions.
- **Manifest:** set autofocus feature as optional.
- **Memory:** manage exceptions.
- **Network:** check for null MAC addresses.
- **Networks:** manage exceptions.
- **Package:** update `conventional-github-releaser` to version 3.0.0.
- **PhoneStatus:** manage exceptions.
- **Security:** upgrade `https-proxy-agent` to latest version.
- **Sensors:** manage exceptions.
- **SIMCard:** do not show SIMCard if state is `SIM_STATE_UNKNOWN`.
- **Software:** manage exceptions.
- **Software:** validate if file size is greater than 0.
- **Test:** improve emulator detection.
- **USB:** manage exceptions.
- **Utils:** return empty string instead of null.
- **Validate:** ensure commit messages are consistent.
- **Videos:** manage exceptions.
- **XML:** replace special characters and allow CDATA sections.

### Features
- **Battery:** add capacity information.
- **BIOS:** add BIOS information and unit tests.
- **Camera:** add camera information.
- **Categories:** add operating system category.
- **CPUs:** add CPU information and unit tests.
- **Drives:** add drive information.
- **Examples:** add Kotlin app example.
- **Inventory:** store JSON and XML results in a file.
- **Networks:** add network information.
- **OS:** add operating system information.
- **SIM:** add support for multiple SIM cards.
- **Software:** add installation date.
- **Storage:** add storage category.
- **Tags:** add support for tags.
- **USB:** add USB device information and unit tests.
- **User:** add user category.
