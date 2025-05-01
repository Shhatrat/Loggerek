
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("android-config-plugin")
    id("compose-plugin")
    id("loggerek-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.base)
            implementation(projects.di)
            implementation("org.jetbrains.compose.material3:material3-window-size-class:1.7.3")
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.base.browser"
}
