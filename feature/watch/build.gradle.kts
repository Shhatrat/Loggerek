import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("io.github.takahirom.roborazzi")
}

roborazzi {
    outputDir.set(file("src/roborazzi"))
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.accompanist.permissions)
        }
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(projects.base)
            implementation(projects.base.color)
            implementation(projects.base.testing)
            implementation(projects.api)
            implementation(projects.manager.watch)
            implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.3")
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        androidUnitTest.dependencies {
            implementation(libs.robolectric)
            implementation(libs.espresso.core)
            implementation(libs.ui.test.junit4)
            implementation("androidx.compose.ui:ui-test-junit4-android:1.7.6")
            implementation("androidx.compose.ui:ui-test-manifest:1.7.6")
            implementation("androidx.compose.ui:ui-test-junit4-android")
            implementation("io.github.takahirom.roborazzi:roborazzi-compose:1.39.0")
            implementation("io.github.takahirom.roborazzi:roborazzi-junit-rule:1.39.0")
            implementation("io.github.takahirom.roborazzi:roborazzi:1.39.0")

        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
        androidInstrumentedTest.dependencies {
            implementation("io.github.takahirom.roborazzi:roborazzi-compose:1.39.0")
            implementation("io.github.takahirom.roborazzi:roborazzi-junit-rule:1.39.0")
            implementation("io.github.takahirom.roborazzi:roborazzi:1.39.0")
        }
        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("io.github.takahirom.roborazzi:roborazzi-compose-desktop:1.39.0")
            implementation(kotlin("test"))
        }
        iosTest.dependencies {
            implementation("io.github.takahirom.roborazzi:roborazzi-compose-ios:1.39.0")
            implementation(kotlin("test"))
        }
        commonTest.dependencies {
            implementation(kotlin("test")) // Wspólny framework do testów
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.watch"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:1.7.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.6")
    implementation(libs.roborazzi)
    api(libs.roborazziRule)
    api(libs.roborazziCompose)

}
