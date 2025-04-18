import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

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
