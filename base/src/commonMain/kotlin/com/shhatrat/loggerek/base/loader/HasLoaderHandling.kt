package com.shhatrat.loggerek.base.loader

interface HasLoaderHandling {
    suspend fun <T> withLoader(delayMs: Long = 0L, action: suspend () -> T): T
}
