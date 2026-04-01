# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Full build (all modules)
./gradlew build

# Build only the library
./gradlew :inventory:assemble

# Run unit tests (JVM)
./gradlew test

# Run instrumentation tests (requires connected device/emulator)
./gradlew connectedAndroidTest

# Run a specific instrumentation test class
./gradlew :inventory:connectedAndroidTest --tests "org.flyve.inventory.HardwareTest"

# Build debug test APK (for Firebase Test Lab)
./gradlew assembleDebugAndroidTest
```

## Architecture

This is a multi-module Android library that collects device inventory data and serializes it as XML/JSON compatible with the **GLPI Native Inventory Protocol**.

### Module Layout

- `inventory/` — The main library (AAR). All inventory logic lives here.
- `example_java/` and `example_kotlin/` — Demo apps that depend on `:inventory` via `implementation project(':inventory')`.

### Core Data Flow

```
InventoryTask.execute()
    → instantiates Categories (ArrayList<Category>)
        → each Category subclass queries Android system APIs
            → stores results as CategoryValue entries (key/value pairs)
    → serializes to XML/JSON via toXML() / toJSON()
    → writes output files, fires OnTaskCompleted callback
```

**`InventoryTask`** is the public entry point. It runs asynchronously and notifies callers via `OnTaskCompleted`.

**`Category`** (extends `LinkedHashMap<String, CategoryValue>`) is the base class for all ~25 inventory categories (Hardware, Software, Networks, Cpus, Memory, Battery, Cameras, Sensors, Bluetooth, Simcards, etc.). Each category knows how to serialize itself to XML and JSON, with optional privacy filtering (`toXMLWithoutPrivateData()` / `toJSONWithoutPrivateData()`).

**`CategoryValue`** holds a single key/value property within a category.

**`Categories`** is the aggregate container that loads all category instances and drives top-level serialization.

### USB subsystem

USB device enumeration is handled via a dedicated `usbManager/` package using a sysfs-based approach (`SysBusUsbManager`, `SysBusUsbDevice`, `SysBusUsbDeviceFactory`) rather than the standard Android USB API.

### SDK targets

- Min SDK: 21 (Android 5.0)
- Compile/Target SDK: 34 (Android 14)
- Java toolchain: JDK 17

## Testing

- **Unit tests** (`src/test/`): JVM-only, minimal — mainly `FunctionUnitTest` for string/network utilities.
- **Instrumentation tests** (`src/androidTest/`): One test class per inventory category plus `InventoryTaskTest` (integration) and `CreateFileTest`. These require a real device or emulator.
- CI runs instrumentation tests on Firebase Test Lab across 7 device/SDK combinations (Nexus 6 SDK 25 through Galaxy S24 SDK 34).

## CI/CD

The GitHub Actions workflow (`.github/workflows/continuous-integration.yml`) triggers on PRs to `main`:
1. Builds with `./gradlew build`
2. Assembles the debug test APK
3. Runs instrumentation tests on Firebase Test Lab (requires `GCP_CREDENTIALS` and `FIREBASE_PROJECT_ID` secrets)
4. Uploads APK and AAR artifacts
