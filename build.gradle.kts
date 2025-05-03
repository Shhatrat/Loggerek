plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.roborazzi) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.2.0"
}

subprojects {
    afterEvaluate {
        if (plugins.hasPlugin("org.jlleitschuh.gradle.ktlint")) {
            extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension>("ktlint") {
                filter {
                    exclude { element ->
                        element.file.path.contains("generated")
                    }
                }
            }
        }
    }
}
