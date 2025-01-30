package com.shhatrat.loggerek.main

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.MoveToIntro
import kotlinx.coroutines.launch

data class MainUiState(
    val nickName: String = "",
    val loader: Loader = Loader(),
    val navigationTabs: List<NavigationHeader> = listOf(),
    val moveToIntro: MoveToIntro = {}
)

class MainViewModel(private val moveToIntroAction: MoveToIntro) :
    BaseViewModel<MainUiState>(MainUiState()) {

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            updateUiState {
                copy(
                    navigationTabs = provideNavigationHeaders(),
                    moveToIntro = moveToIntroAction
                )
            }
        }
    }

    private fun provideNavigationHeaders() = NavigationHeader.Main.getAll()
}
