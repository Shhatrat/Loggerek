package com.shhatrat.loggerek.main

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.launch

data class MainUiState(
    val nickName: String = "",
    val loader: Loader = Loader(),
    val removeData: () -> Unit = {},
    val navigationTabs: List<NavigationHeader> = listOf()
)

class MainViewModel(
    private val navigateToIntroScreen: () -> Unit,
    private val api: Api,
    private val repository: Repository,
) : BaseViewModel<MainUiState>(MainUiState()) {

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            updateUiState {
                copy(
                    navigationTabs = provideNavigationHeaders(),
                    removeData = {
                    repository.token.remove()
                    repository.tokenSecret.remove()
                    navigateToIntroScreen()
                })
            }
        }
    }

    private fun provideNavigationHeaders() = NavigationHeader.values().asList()
}
