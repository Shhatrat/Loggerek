import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("android-config-plugin")
    id("loggerek-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.compose)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(projects.base.color)
            implementation(libs.coil.compose)
            implementation(libs.coil.svg)
            implementation(libs.coil.network.ktor3)
            implementation(projects.base.typeface)
            implementation(compose.components.resources)
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.material3.window.size.class1)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.base"
}
