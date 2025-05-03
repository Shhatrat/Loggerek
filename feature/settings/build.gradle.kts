
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("io.github.takahirom.roborazzi")
    id("feature-plugin")
    id("coil-plugin")
}

roborazzi {
    outputDir.set(file("src/roborazzi"))
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.manager.account)
            implementation(projects.manager.watch)
            implementation(projects.base)
            implementation(projects.base.color)
            implementation(projects.base.testing)
            implementation(projects.api)
        }
        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.settings"
}
