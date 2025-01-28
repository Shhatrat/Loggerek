package com.shhatrat.loggerek.base.error

import com.shhatrat.loggerek.base.Error

interface HasErrorHandling {

    suspend fun <T> withSuspendErrorHandling(
        error: Error? = null,
        onError: (() -> Unit)? = null,
        action: suspend () -> T
    )

    fun withErrorHandling(action: () -> Unit)
}