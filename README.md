# Loggerek

### Compose multiplaftorm application for opencaching

## Platforms:
- Android
- Ios
- wasm
- desktop

## Features:
- Authorization in (opencaching.pl)[www.opencaching.pl]
- Logging caches



--- 
### Testing
#### Navigation
module `composeApp`, target `androidInstrumentedTest`, run with `connectedAndroidTest`
#### ViewModel unit tests

`com.shhatrat.loggerek.intro.authorizate.AuthViewModelTest`
Click the green run icon in the gutter next to the `AuthViewModelTest` class


#### Ui testing

`com.shhatrat.loggerek.introIntroScreenTest`

android
```
./gradlew :feature:intro:connectedAndroidTest
```

ios
```
./gradlew :feature:intro:iosSimulatorArm64Test
```

desktop
```
./gradlew :feature:intro:desktopTest
```

wasm
```
./gradlew :feature:intro:wasmJsTest
```

--- 

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop, Server.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - `commonMain` is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.
