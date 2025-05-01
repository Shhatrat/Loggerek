
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
            implementation(libs.material3.window.size.class1)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.base.browser"
}
