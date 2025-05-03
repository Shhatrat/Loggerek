
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    kotlin("plugin.serialization") version "2.0.0"
    id("android-config-plugin")
    id("loggerek-plugin")
}

kotlin {
    sourceSets {

        val mobile by creating {
            dependsOn(commonMain.get())
        }

        val iosMain by getting {
            dependsOn(mobile)
        }

        val iosX64Main by getting {
            dependsOn(iosMain)
        }

        val iosArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val androidMain by getting {
            dependsOn(mobile)
            dependencies {
                implementation(projects.wearShared)
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.1")
                api(libs.play.services.wearable)
                implementation("com.google.android.gms:play-services-location:21.0.1")
                implementation("com.garmin.connectiq:ciq-companion-app-sdk:2.2.0")
            }
        }

        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(projects.base.testing)
            implementation(projects.repository)
            implementation(projects.api)
            implementation(projects.di)
            implementation(projects.manager.account)
            implementation(projects.base)
            implementation(libs.ktor.serialization.kotlinx.json)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.manager.watch"
}

