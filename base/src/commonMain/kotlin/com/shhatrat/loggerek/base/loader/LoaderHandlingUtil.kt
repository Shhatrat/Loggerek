package com.shhatrat.loggerek.base.loader

import kotlinx.coroutines.delay

class LoaderHandlingUtil(val loaderAction: (Boolean) -> Unit) : HasLoaderHandling{
    override suspend fun <T> withLoader(delayMs: Long, action: suspend () -> T): T {
        try {
            loaderAction(true)
            delay(delayMs)
            val result = action()
            return result
        }
        finally {
            loaderAction(false)
        }
    }
}
