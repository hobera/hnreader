# HN Reader

[![CircleCI](https://circleci.com/gh/hobera/hnreader/tree/master.svg?style=shield&circle-token=6459c6a26322b131d73f1d9bacd2d6f1b68bdb9a)](https://circleci.com/gh/hobera/hnreader/tree/master)

A simple Hacker News (https://news.ycombinator.com) reader app using the Hacker News API (https://github.com/HackerNews/API).

## Project Status & Modernization (Phase 1 Updates)

This project has undergone a first phase of modernization to update its core dependencies and build configuration. Key changes include:

*   **Build Tools:**
    *   Upgraded to Android Gradle Plugin version 8.2.2.
    *   Upgraded to Gradle version 8.2.
*   **AndroidX Migration:**
    *   Migrated from legacy Android Support Libraries to AndroidX libraries.
*   **API Levels:**
    *   `compileSdkVersion` updated to 33.
    *   `targetSdkVersion` updated to 33.
*   **Crash Reporting:**
    *   Replaced Fabric Crashlytics with Firebase Crashlytics.
*   **Java Version:**
    *   The project continues to use Java 8, with Retrolambda removed as modern AGP versions provide native support.

## Building

To build this project, you will need:

*   A configured Android SDK. Ensure your environment (e.g., `ANDROID_HOME` environment variable or `local.properties` file with `sdk.dir`) points to a valid Android SDK installation.
*   Java Development Kit (JDK) compatible with AGP 8.2.2 and Gradle 8.2 (typically JDK 17).

Then, you can build using Gradle:
```bash
./gradlew assembleDebug
```

**Note on CI (CircleCI):** The existing CircleCI configuration (`circle.yml`) was designed for the older project structure and dependencies. It will require a significant review and update in a subsequent phase to align with the current modernized setup (including AndroidX, updated AGP/Gradle, JDK requirements, and potential SDK environment setup for CI). The current badge may not reflect the build status of the modernized codebase.
