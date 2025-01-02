package com.shhatrat.loggerek

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform