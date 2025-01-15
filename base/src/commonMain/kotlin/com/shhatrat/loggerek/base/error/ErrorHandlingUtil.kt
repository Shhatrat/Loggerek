package com.shhatrat.loggerek.base.error

import com.shhatrat.loggerek.base.Error

class ErrorHandlingUtil(private val errorAction: (Error) -> Unit) : HasErrorHandling{
    override suspend fun <T> withSuspendErrorHandling(
        error: Error?,
        onError: (() -> Unit)?,
        action: suspend () -> T
    ) {
        try {
            action()
        } catch (e: Exception) {
            errorAction.invoke(error?:Error(e.message?:""))
            onError?.invoke()
        }
    }

    override fun withErrorHandling(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            errorAction.invoke(Error(e.message?:""))
        }
    }
}