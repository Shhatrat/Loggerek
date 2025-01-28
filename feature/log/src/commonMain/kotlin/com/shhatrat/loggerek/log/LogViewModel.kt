package com.shhatrat.loggerek.log

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.OpencachingParam
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.ButtonAction
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.composable.MultiTextFieldModel
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
import com.shhatrat.loggerek.manager.log.LogManager
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource

data class LogUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,

    val geocacheData: GeocacheData? = null
)

data class GeocacheData(
    val title: String,
    val titleLink: String,
    val ratingData: RatingData? = null,
    val logTypeData: LogTypeData,
    val description: MultiTextFieldModel,
    val myNotes: MultiTextFieldModel,
    val password: MultiTextFieldModel?,
    val sendAction: ButtonAction,
    val resetAction: ButtonAction,
)

data class RatingData(
    val showRating: Boolean = false,
    val rating: Int? = null,
    val starsOnChanged: (Int) -> Unit,
    val recommendation: Boolean = false,
    val recommendationPossible: Boolean,
    val recommendationChanged: (Boolean) -> Unit,
)

data class LogTypeData(
    val selectedIndex: Int,
    val types: List<StringResource>,
    val onChangedIndex: (Int) -> Unit
)


class LogViewModel(
    private val cacheId: String,
    private val logManager: LogManager
) : BaseViewModel<LogUiState>(LogUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }

    private var logTypeData: FilledData = FilledData()

    data class FilledData(
//        val logType: LogType? = null,
        val selectedType: LogType? = null,
        val recommended: Boolean = false,
    )

    private val defaultLogTypeIndex = 0

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            val cache = logManager.getCache(cacheId)
            logTypeData = logTypeData.copy(selectedType = cache.type.logType.logTypes.first())
            updateUiState {
                copy(
                    geocacheData = GeocacheData(
                        title = cache.name,
                        titleLink = cache.url,
                        ratingData = RatingData(
                            showRating = cache.type.logType.logTypes[defaultLogTypeIndex].canRate,
                            starsOnChanged = {
                                updateUiState { copy(geocacheData = geocacheData?.copy(ratingData = geocacheData.ratingData?.copy(rating = it))) }
                            },
                            recommendationPossible = logTypeData.selectedType?.canBeRecommended
                                ?: false,
                            recommendationChanged = {
//                                logTypeData = logTypeData.copy(recommended = it)
                                updateUiState { copy(geocacheData = geocacheData?.copy(ratingData = geocacheData.ratingData?.copy(recommendation = it))) }
                            }),
                        logTypeData = LogTypeData(
                            selectedIndex = defaultLogTypeIndex,
                            types = cache.type.logType.logTypes.map { it.textRes },
                            onChangedIndex = { changed ->
                                updateUiState {
                                    copy(
                                        geocacheData = geocacheData?.copy(
                                            ratingData = geocacheData.ratingData?.copy(
                                                showRating = cache.type.logType.logTypes[changed].canRate,
                                                ),
                                            logTypeData = geocacheData.logTypeData.copy(
                                                selectedIndex = changed
                                            )
                                        )
                                    )
                                }
                                setupPassword(cache)
                            }),
                        description = MultiTextFieldModel { changedText ->
                            updateUiState {
                                copy(
                                    geocacheData = geocacheData?.copy(
                                        description = geocacheData.description.copy(text = changedText)
                                    )
                                )
                            }
                        },
                        myNotes = MultiTextFieldModel(text = cache.myNotes?:"") { changedText ->
                            updateUiState {
                                copy(
                                    geocacheData = geocacheData?.copy(
                                        myNotes = geocacheData.myNotes.copy(text = changedText)
                                    )
                                )
                            }
                        },
                        password = null,
                        sendAction = {
                            viewModelScope.launch {
                                logManager.saveNote(cacheId, state.value.geocacheData?.myNotes?.text?:"", cache.myNotes?:"")
                            }
                        },
                        resetAction = {
                            resetData()
                        },
                    )
                )
            }
            setupPassword(cache)
        }
    }

    private fun setupPassword(cache: Geocache){
        val shouldShow = cache.requirePassword && cache.type.logType.logTypes[state.value.geocacheData?.logTypeData?.selectedIndex?:defaultLogTypeIndex].canHasPassword
        if(shouldShow){
            updateUiState {
                copy(
                    geocacheData = geocacheData?.copy(password = MultiTextFieldModel { changedText ->
                        updateUiState {
                            copy(
                                geocacheData = geocacheData?.password?.copy(text = changedText)
                                    ?.let {
                                        geocacheData.copy(
                                            password = it
                                        )
                                    }
                            )
                        }
                    }))}
        }else{
            updateUiState { copy(geocacheData = geocacheData?.copy(password = null)) }
        }
    }

    private fun resetData() {
        logTypeData = FilledData()
        updateUiState {
            copy(
                geocacheData = geocacheData?.copy(
                    description = geocacheData.description.copy(""),
                    myNotes = geocacheData.myNotes.copy(""),
                    password = geocacheData.password?.copy("")
                )
            )
        }
    }
}
