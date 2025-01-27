package com.shhatrat.loggerek.settings

import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.base.BaseViewModel
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.Loader
import com.shhatrat.loggerek.base.MoveToIntro
import com.shhatrat.loggerek.base.error.ErrorHandlingUtil
import com.shhatrat.loggerek.base.loader.LoaderHandlingUtil
import loggerek.feature.settings.generated.resources.Res
import loggerek.feature.settings.generated.resources.accountTitle
import loggerek.feature.settings.generated.resources.logout
import loggerek.feature.settings.generated.resources.logsTitle
import loggerek.feature.settings.generated.resources.savePasswordToMyNotes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class SettingsUiState(
    val settings: List<SettingsItem> = emptyList(),
    val loader: Loader = Loader(),
    val error: Error? = null,
)
sealed class SettingsItem(open val descriptionRes: StringResource) {

    data class SettingsTitle(
        override val descriptionRes: StringResource,
    ): SettingsItem(descriptionRes)

    data class SettingsSwitch(
        override val descriptionRes: StringResource,
        val checked: Boolean,
        val onChecked: (Boolean) -> Unit
    ): SettingsItem(descriptionRes)

    data class SettingsButton(
        override val descriptionRes: StringResource,
        val iconRes: DrawableResource,
        val action: () -> Unit
    ): SettingsItem(descriptionRes)
}
class SettingsViewModel(
    private val moveToIntro: MoveToIntro,
    private val accountManager: AccountManager
) : BaseViewModel<SettingsUiState>(SettingsUiState()) {

    private val loaderHandlingUtil =
        LoaderHandlingUtil { loaderAction -> updateUiState { copy(loader = Loader(loaderAction)) } }

    private val errorHandlingUtil =
        ErrorHandlingUtil { error -> updateUiState { copy(error = error) } }

    override fun onStart() {
        super.onStart()
        updateUiState {
            copy(
                settings = listOf(
                    SettingsItem.SettingsTitle(Res.string.logsTitle),
                    SettingsItem.SettingsSwitch(Res.string.savePasswordToMyNotes, checked = true){

                    },
                    SettingsItem.SettingsTitle(Res.string.accountTitle),
                    SettingsItem.SettingsButton(Res.string.logout, Res.drawable.logout, {
                        accountManager.logout()
                        moveToIntro()
                    })
                )
            )
        }
    }
}
