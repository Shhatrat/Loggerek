package com.shhatrat.loggerek.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.StringResource

data class Loader(val active: Boolean = false)

class Error(val descriptionText: String = "", val retry: Retry? = null)

data class Retry(val description: String? = null,
                     val action: (() -> Unit)? = null)

open class BaseViewModel<T>(initialState: T) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<T> = _state

    open fun onStart(){}

    suspend fun <T>actionWithLoader(loaderAction: (Boolean) -> Unit,
                                    action: suspend () -> T,
                                    delayMs: Long): T{
        loaderAction(true)
        delay(delayMs)
        val result = action()
        loaderAction(false)
        return result
    }

    protected fun updateState(newState: T) {
        _state.value = newState
    }

    protected fun updateUiState(update: T.() -> T) {
        _state.update { currentState -> currentState.update() }
    }
}