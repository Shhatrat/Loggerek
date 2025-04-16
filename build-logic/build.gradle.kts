plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:8.3.2")
//    compileOnly("org.jetbrains.kotlin.multiplatform:2.1.0")
//    compileOnly("com.android.library:8.5.2")
//    implementation(libs.plugins.kotlinMultiplatform)
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")

//    compileOnly(libs.plugins.androidLibrary)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

}

gradlePlugin {
    plugins {
        register("androidConfigPlugin") {
            id = "android-config-plugin"
            implementationClass = "AndroidConfigPlugin"
        }
    }
}