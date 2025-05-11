rootProject.name = "Loggerek"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":composeApp")
include(":api")
include(":repository")
include(":intro")
include(":di")
include(":feature:intro")
include(":feature:main")
include(":feature:profile")
include(":feature:settings")
include(":feature:watch")
include(":feature:log")
include(":base")
include(":base:color")
include(":base:testing")
include(":base:typeface")
include(":base:browser")
include(":base:cache")
include(":manager:account")
include(":manager:watch")
include(":manager:log")
include(":wear")
include(":wearShared")
