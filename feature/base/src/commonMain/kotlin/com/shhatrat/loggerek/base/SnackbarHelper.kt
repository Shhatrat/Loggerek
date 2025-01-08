package com.shhatrat.loggerek.base

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object SnackBarHelper {

    @Composable
    fun ProvideSnackBar(snackbarHostState: SnackbarHostState){
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    backgroundColor = Color(0xFFB71C1C),
                    contentColor = Color.White,
                    actionColor = Color.White
                )
            }
        )
    }

    suspend fun handle(snackBarHostState: SnackbarHostState, errorObj: Error?){
        errorObj?.let { error ->
            val result = snackBarHostState.showSnackbar(message = error.descriptionText, actionLabel = error.retry?.description)
            if(result == SnackbarResult.ActionPerformed){
                error.retry?.action?.invoke()
            }
        }
    }
}