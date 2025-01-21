package com.shhatrat.loggerek.intro.splash

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Loader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class IntroUiState(
    val loader: Loader = Loader(true),
    val buttonAction: (() -> Unit)? = null
)

class IntroViewModel(
    private val navigateToMainScreen: () -> Unit,
    private val navigateToAuthScreen: () -> Unit,
    private val accountManager: AccountManager
) : BaseViewModel<IntroUiState>(IntroUiState()) {

    private suspend fun <T> withLoader(delayMs: Long = 0L, action: suspend () -> T): T {
        return actionWithLoader(
            loaderAction = { updateUiState { copy(loader = Loader(it)) } },
            action = action,
            delayMs = delayMs)
    }

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            checkIsReadyToMainOrSetupButton()
        }
    }

    private suspend fun checkIsReadyToMainOrSetupButton() {
        if (withLoader { accountManager.isUserLogged() }) {
            withLoader { delay(500) }
            navigateToMainScreen()
        }
        else {
            updateUiState { copy(buttonAction = {
                navigateToAuthScreen()
            })
            }
        }
    }
}