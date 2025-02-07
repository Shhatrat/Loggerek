package com.shhatrat.loggerek.settings

import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.MoveToIntro
import com.shhatrat.loggerek.base.MoveToWatch
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
import com.shhatrat.loggerek.manager.watch.Watch
import loggerek.feature.settings.generated.resources.Res
import loggerek.feature.settings.generated.resources.accountTitle
import loggerek.feature.settings.generated.resources.logout
import loggerek.feature.settings.generated.resources.logsTitle
import loggerek.feature.settings.generated.resources.savePasswordToMyNotes
import loggerek.feature.settings.generated.resources.tryMixedPassword
import loggerek.feature.settings.generated.resources.watchIntegration
import org.jetbrains.compose.resources.StringResource

data class SettingsUiState(
    val savePassword: SettingsItem.SettingsSwitch? = null,
    val mixedPassword: SettingsItem.SettingsSwitch? = null,
    val watch: SettingsItem.SettingsButton? = null,
    val logout: SettingsItem.SettingsButton? = null,
    val loader: Loader = Loader(),
    val error: Error? = null,
) {
    fun settings() = listOfNotNull(
        SettingsItem.SettingsTitle(Res.string.logsTitle),
        savePassword,
        mixedPassword,
        watch,
        SettingsItem.SettingsTitle(Res.string.accountTitle),
        logout
    )
}

sealed class SettingsItem(open val descriptionRes: StringResource) {

    data class SettingsTitle(
        override val descriptionRes: StringResource,
    ) : SettingsItem(descriptionRes)

    data class SettingsSwitch(
        override val descriptionRes: StringResource,
        val checked: Boolean,
        val onChecked: (Boolean) -> Unit
    ) : SettingsItem(descriptionRes)

    data class SettingsButton(
        override val descriptionRes: StringResource,
        val iconResPath: String,
        val action: () -> Unit
    ) : SettingsItem(descriptionRes)
}

class SettingsViewModel(
    private val moveToIntro: MoveToIntro,
    private val moveToWatch: MoveToWatch,
    private val accountManager: AccountManager,
    private val watchManager: Watch?
) : BaseViewModel<SettingsUiState>(SettingsUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }

    override fun onStart() {
        super.onStart()
        updateUiState {
            copy(
                savePassword = SettingsItem.SettingsSwitch(
                    Res.string.savePasswordToMyNotes,
                    checked = true
                ) { isChecked ->
                    updateUiState {
                        copy(savePassword = savePassword?.copy(checked = isChecked))
                    }
                    accountManager.savePassword = isChecked
                },
                mixedPassword = SettingsItem.SettingsSwitch(
                    Res.string.tryMixedPassword,
                    checked = true
                ) { isChecked ->
                    updateUiState {
                        copy(mixedPassword = mixedPassword?.copy(checked = isChecked))
                    }
                    accountManager.tryMixedPassword = isChecked
                },
                logout = SettingsItem.SettingsButton(Res.string.logout, "drawable/logout.svg") {
                    accountManager.logout()
                    moveToIntro()
                },
                watch = if (watchManager == null) null else SettingsItem.SettingsButton(
                    Res.string.watchIntegration,
                    "drawable/watch.svg"
                ) {
                    moveToWatch()
                }
            )
        }
    }
}
