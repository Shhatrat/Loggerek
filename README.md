# Loggerek

### Compose multiplaftorm application for opencaching

## Platforms:

- Android
- Ios
- wasm
- desktop

## Features:

- Authorization in [opencaching.pl](www.opencaching.pl)
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

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle
task.


### TODO

- Loggerek:feature:settings [recordRoborazziJvm]
- Loggerek:feature:log [recordRoborazziJvm]