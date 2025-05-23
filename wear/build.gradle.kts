plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.shhatrat.loggerek"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shhatrat.loggerek"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(libs.androidx.wear.tooling.preview)
    val composeBom = platform(libs.androidx.compose.bom)
    // General compose dependencies
    implementation(composeBom)

//    implementation("androidx.compose.compose-bom:2025.02.00")
    implementation(libs.play.services.wearable)
    implementation(projects.manager.watch)
    implementation(projects.wearShared)
    implementation(projects.api)
    implementation(projects.base.color)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.horologist.compose.layout)
    implementation(libs.horologist.compose.material)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.tiles.tooling)
}
