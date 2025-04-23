plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("android-config-plugin")
    id("loggerek-plugin")
    id("ktor-plugin")
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
}

ktlint {
    filter {
        exclude { element ->
            val path = element.file.path
            path.contains("generated")
        }
    }
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(compose.components.resources)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.api"
}
