package com.shhatrat.loggerek.profile

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: FullUser? = null,
    val loader: Loader = Loader(),
    val error: Error? = null,
    val removeData: () -> Unit = {}
)

class ProfileViewModel(
    private val accountManager: AccountManager
) : BaseViewModel<ProfileUiState>(ProfileUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            errorHandlingUtil.withSuspendErrorHandling {
                loaderHandlingUtil.withLoader {
                    val user = accountManager.getFullUserData()
                    updateUiState { copy(user = user) }
                }
            }
        }
    }
}
