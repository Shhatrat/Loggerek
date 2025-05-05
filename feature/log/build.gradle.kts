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
            implementation(libs.compottie)
            implementation(projects.base)
            implementation(projects.base.color)
            implementation(projects.base.browser)
            implementation(projects.base.testing)
            implementation(projects.api)
            implementation(projects.manager.account)
            implementation(projects.manager.log)
            implementation(libs.datetime.wheel.picker)
            implementation(libs.kotlinx.datetime)
        }
        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.log"
}
