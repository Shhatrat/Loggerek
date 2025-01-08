package com.shhatrat.loggerek.intro.authorizate

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import kotlinx.coroutines.launch

data class AuthUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,
    val startButton: (() -> Unit)? = null,
    val openBrowserAction: (() -> Unit)? = null,
    val browserLink: String? = null,
    val pastePinAction: ((String) -> Unit)? = null,
)

class AuthViewModel(
    private val navigateToMain: () -> Unit,
    private val accountManager: AccountManager
) : BaseViewModel<AuthUiState>(AuthUiState()) {

    private suspend fun <T> withLoader(delayMs: Long = 0L, action: suspend () -> T): T {
        return actionWithLoader(
            loaderAction = { updateUiState { copy(loader = Loader(it)) } },
            action = action,
            delayMs = delayMs
        )
    }

    private suspend fun <T>withSuspendErrorHandling(error: Error? = null,
                                                    onError: (() -> Unit)? = null,
                                                    action: suspend () -> T): T {
        return try {
            action()
        } catch (e: Exception) {
            updateUiState { copy(error = error?:Error(e.message!!)) }
            onError?.invoke()
            throw e
        }
    }

    private fun withErrorHandling(action: () -> Unit) {
        try {
            action()
        } catch (e: Exception) {
            updateUiState { copy(error = Error(e.message!!)) }
        }
    }

    override fun onStart() {
        super.onStart()
        setupStartState()
    }

    private fun setupStartState(){
        updateUiState { copy(startButton = { withErrorHandling { startAction() } },
            loader = loader.copy(false),
            pastePinAction = null,
            browserLink = null,
            openBrowserAction = null) }
    }

    private fun startAction() {
        val error = Error("blad")
        viewModelScope.launch {
            val response =
                withSuspendErrorHandling(
                    error = error,
                    onError = { setupStartState() }) {
                    withLoader {
                        accountManager.startAuthorizationProcess() }.apply {
                    }
                }
            updateUiState {
                copy(
                    loader = loader.copy(false),
                    openBrowserAction = { openUrl(response.url) },
                    browserLink = response.url,
                    startButton = null,
                    pastePinAction = {
                        viewModelScope.launch {
                            withSuspendErrorHandling(
                                error = error,
                                onError = { setupStartState() }) {
                                withLoader { response.pastePinAction(it) }
                                updateUiState {
                                    copy(
                                        openBrowserAction = null,
                                        browserLink = null,
                                        pastePinAction = null
                                    )
                                }
                                navigateToMain()
                            }
                        }
                    }
                )
            }
        }
    }

    private fun openUrl(url: String) {
        //TODO
    }
}