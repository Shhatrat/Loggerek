package com.shhatrat.loggerek.intro.authorizate

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
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

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }

    override fun onStart() {
        super.onStart()
        setupStartState()
    }

    private fun setupStartState() {
        updateUiState {
            copy(
                startButton = { errorHandlingUtil.withErrorHandling { startAction() } },
                loader = loader.copy(false),
                pastePinAction = null,
                browserLink = null,
                openBrowserAction = null
            )
        }
    }

    private fun startAction() {
        val error = Error("blad")
        viewModelScope.launch {
            errorHandlingUtil.withSuspendErrorHandling(
                error = error,
                onError = { setupStartState() }) {
                val response = loaderHandlingUtil.withLoader {
                    accountManager.startAuthorizationProcess()
                }
                updateUiState {
                    copy(
                        loader = loader.copy(false),
                        openBrowserAction = { openUrl(response.url) },
                        browserLink = response.url,
                        startButton = null,
                        pastePinAction = {
                            viewModelScope.launch {
                                errorHandlingUtil.withSuspendErrorHandling(
                                    error = error,
                                    onError = { setupStartState() }) {
                                    loaderHandlingUtil.withLoader { response.pastePinAction(it) }
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
    }

    private fun openUrl(url: String) {
        //TODO
    }
}