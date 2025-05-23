package com.shhatrat.loggerek.search

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.MoveToLogCache
import com.shhatrat.loggerek.base.composable.MultiTextFieldModel
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
import com.shhatrat.loggerek.manager.log.LogManager
import kotlinx.coroutines.launch

data class SearchUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,
    val search: MultiTextFieldModel = MultiTextFieldModel(),
    val caches: List<Geocache> = listOf(),
    val move: MoveToLogCache? = null
)

class SearchViewModel(
    private val moveToLogCache: MoveToLogCache,
    private val logManager: LogManager
) : BaseViewModel<SearchUiState>(SearchUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    override fun onStart() {
        onStartOnceAction = {
            updateUiState { copy(move = moveToLogCache) }
            viewModelScope.launch {
                updateUiState {
                    copy(search = MultiTextFieldModel {
                        viewModelScope.launch {
                            updateUiState {
                                copy(search = search.copy(text = it))
                            }
                            downloadCaches(it)
                        }
                    })
                }
            }
            downloadCaches("")
        }
        super.onStart()
    }

    private fun downloadCaches(name: String) {
        viewModelScope.launch {
            loaderHandlingUtil.withLoader {
                val results = logManager.searchByName(name)
                updateUiState { copy(caches = results) }
            }
        }
    }
}