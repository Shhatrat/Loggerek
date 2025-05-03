import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

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
            implementation(projects.base)
            implementation(projects.manager.account)
            implementation(projects.feature.watch)
            implementation(projects.api)
            implementation(projects.feature.profile)
            implementation(projects.feature.log)
            implementation(projects.feature.settings)
            implementation(projects.repository)

            api(compose.material3AdaptiveNavigationSuite)
            api(libs.adaptive.navigation)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.viewmodel.navigation)
        }

        jvmTest.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.shhatrat.loggerek.main"
}