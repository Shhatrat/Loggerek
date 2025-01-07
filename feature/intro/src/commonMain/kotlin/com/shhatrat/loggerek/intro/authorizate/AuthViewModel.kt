package com.shhatrat.loggerek.intro.authorizate

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Loader
import kotlinx.coroutines.launch

data class AuthUiState(
    val loader: Loader = Loader(),
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

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            val response = withLoader { accountManager.startAuthorizationProcess() }
            response.url
            updateUiState {
                copy(
                    loader = loader.copy(false),
                    openBrowserAction = { openUrl(response.url) },
                    browserLink = response.url,
                    pastePinAction = {
                        viewModelScope.launch {
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
                )
            }
        }
    }

    private fun openUrl(url: String) {
        //TODO
    }
}