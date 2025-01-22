package com.shhatrat.loggerek.log

import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.MoveToIntro
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil

data class LogUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,
)


class LogViewModel(
    private val cacheId: String
) : BaseViewModel<LogUiState>(LogUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }

    override fun onStart() {
        super.onStart()
    }
}
