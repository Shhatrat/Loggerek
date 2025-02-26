plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.composeCompiler)
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.1")
    implementation("androidx.wear.compose:compose-navigation:1.4.1")
    implementation("com.google.android.horologist:horologist-compose-layout:0.7.5-alpha")
    implementation("com.google.android.horologist:horologist-compose-material:0.7.5-alpha")
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.tiles.tooling)
}
