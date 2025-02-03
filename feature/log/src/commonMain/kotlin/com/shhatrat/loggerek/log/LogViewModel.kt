package com.shhatrat.loggerek.log

import androidx.lifecycle.viewModelScope
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogResponse
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.SubmitLogData
import com.shhatrat.loggerek.api.model.isFound
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.ButtonAction
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.browser.BrowserUtil
import com.shhatrat.loggerek.base.composable.MultiTextFieldModel
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
import com.shhatrat.loggerek.manager.log.LogManager
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class LogUiState(
    val loader: Loader = Loader(),
    val error: Error? = null,
    val success: Success? = null,
    val geocacheData: GeocacheData? = null
)

class Success(val onFinished: (() -> Unit)? = null)

data class GeocacheData(
    val title: String,
    val typeIcon: DrawableResource,
    val isFound: Boolean,
    val onClick: () -> Unit,
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
    private val logManager: LogManager,
    private val accountManager: AccountManager,
    private val browserUtil: BrowserUtil
) : BaseViewModel<LogUiState>(LogUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }


    private val defaultLogTypeIndex = 0

    override fun onStart() {
        super.onStart()
        viewModelScope.launch {
            val cache = logManager.getCache(cacheId)
            logManager.logCapabilities(cacheId)
            updateUiState {
                copy(
                    geocacheData = GeocacheData(
                        title = cache.name,
                        onClick = { browserUtil.openWithUrl(cache.url) },
                        ratingData = RatingData(
                            showRating = cache.type.logType.logTypes[defaultLogTypeIndex].canRate,
                            starsOnChanged = {
                                updateUiState {
                                    copy(
                                        geocacheData = geocacheData?.copy(
                                            ratingData = geocacheData.ratingData?.copy(
                                                rating = it
                                            )
                                        )
                                    )
                                }
                            },
                            recommendationPossible = cache.type.logType.logTypes[defaultLogTypeIndex].canBeRecommended,
                            recommendationChanged = {
                                updateUiState {
                                    copy(
                                        geocacheData = geocacheData?.copy(
                                            ratingData = geocacheData.ratingData?.copy(
                                                recommendation = it
                                            )
                                        )
                                    )
                                }
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
                        myNotes = MultiTextFieldModel(text = cache.myNotes ?: "") { changedText ->
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
                                errorHandlingUtil.withSuspendErrorHandling {
                                    loaderHandlingUtil.withLoader {
                                        logManager.saveNote(
                                            cacheId,
                                            state.value.geocacheData?.myNotes?.text ?: "",
                                            cache.myNotes ?: ""
                                        )
                                        val logType: LogType = cache.type.logType.logTypes[state.value.geocacheData?.logTypeData?.selectedIndex
                                            ?: defaultLogTypeIndex]
                                        if(logType == LogType.FOUND && cache.requirePassword && accountManager.tryMixedPassword){
                                            var response: LogResponse? = null
                                            for (alternative in PasswordHelper.getAlternatives(state.value.geocacheData?.password?.text ?: "")) {
                                                response= logManager.submitLog(
                                                    SubmitLogData(
                                                        cacheId = cacheId,
                                                        logType = logType,
                                                        rating = state.value.geocacheData?.ratingData?.rating,
                                                        comment = state.value.geocacheData?.description?.text
                                                            ?: "",
                                                        reccomend = state.value.geocacheData?.ratingData?.recommendation
                                                            ?: false,
                                                        password = alternative
                                                    )
                                                )
                                                if(response.success){
                                                    break
                                                }
                                            }
                                            if(accountManager.savePassword && cache.requirePassword && response!!.success){
                                                logManager.saveNote(
                                                    cacheId,
                                                    (state.value.geocacheData?.myNotes?.text ?: "").plus("\n${state.value.geocacheData?.password?.text}"),
                                                    state.value.geocacheData?.myNotes?.text ?: ""
                                                )
                                            }
                                            if (response!!.success) {
                                                updateUiState {
                                                    copy(success = Success {
                                                        updateUiState { copy(success = null) }
                                                    })
                                                }
                                            } else {
                                                updateUiState { copy(error = Error(response.message)) }
                                            }
                                        }else{
                                            val response = logManager.submitLog(
                                                SubmitLogData(
                                                    cacheId = cacheId,
                                                    logType = cache.type.logType.logTypes[state.value.geocacheData?.logTypeData?.selectedIndex
                                                        ?: defaultLogTypeIndex],
                                                    rating = state.value.geocacheData?.ratingData?.rating,
                                                    comment = state.value.geocacheData?.description?.text
                                                        ?: "",
                                                    reccomend = state.value.geocacheData?.ratingData?.recommendation
                                                        ?: false,
                                                    password = state.value.geocacheData?.password?.text
                                                )
                                            )
                                            if (response.success) {
                                                updateUiState {
                                                    copy(success = Success {
                                                        updateUiState { copy(success = null) }
                                                        onStart()
                                                    })
                                                }
                                            } else {
                                                updateUiState { copy(error = Error(response.message)) }
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        resetAction = {
                            resetData()
                        },
                        typeIcon = cache.type.iconRes,
                        isFound = cache.isFound()
                    )
                )
            }
            setupPassword(cache)
        }
    }

    private fun setupPassword(cache: Geocache) {
        val shouldShow =
            cache.requirePassword && cache.type.logType.logTypes[state.value.geocacheData?.logTypeData?.selectedIndex
                ?: defaultLogTypeIndex].canHasPassword
        if (shouldShow) {
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
                    })
                )
            }
        } else {
            updateUiState { copy(geocacheData = geocacheData?.copy(password = null)) }
        }
    }

    private fun resetData() {
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
