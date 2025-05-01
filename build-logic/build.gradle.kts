plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.android.tools.build:gradle:8.3.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("androidConfigPlugin") {
            id = "android-config-plugin"
            implementationClass = "AndroidConfigPlugin"
        }
        register("loggerekPlugin") {
            id = "loggerek-plugin"
            implementationClass = "LoggerekPlugin"
        }
        register("ktorPlugin") {
            id = "ktor-plugin"
            implementationClass = "KtorPlugin"
        }
        register("composePlugin") {
            id = "compose-plugin"
            implementationClass = "ComposePlugin"
        }
        register("coilPlugin") {
            id = "coil-plugin"
            implementationClass = "CoilPlugin"
        }
    }
}