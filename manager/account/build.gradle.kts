
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("android-config-plugin")
    id("loggerek-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(projects.repository)
            implementation(projects.api)
            implementation(projects.base.testing)
            implementation(libs.corotuines.core)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.account"
}
