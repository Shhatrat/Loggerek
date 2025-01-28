package com.shhatrat.loggerek.base

enum class Type {
    ANDROID, IOS, JVM
}

fun String.addPackagePrefix(type: Type, packageName: String): String {
    val newValue = "$type-${packageName}"
    return this.replace(packageName, newValue)
}