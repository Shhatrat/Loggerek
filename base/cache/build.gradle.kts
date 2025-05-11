import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("android-config-plugin")
    id("compose-plugin")
    id("loggerek-plugin")
    id("coil-plugin")
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.base)
            implementation(libs.material3.window.size.class1)
            implementation(projects.api)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.base.cache"
}
