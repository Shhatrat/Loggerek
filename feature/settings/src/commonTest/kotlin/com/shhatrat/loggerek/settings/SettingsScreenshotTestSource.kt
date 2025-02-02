package com.shhatrat.loggerek.settings

import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import com.shhatrat.loggerek.base.testing.TestItem
import com.shhatrat.loggerek.base.testing.getTestItems
import loggerek.feature.settings.generated.resources.Res
import loggerek.feature.settings.generated.resources.tryMixedPassword

class SettingsScreenshotTestSource {

    fun settingsScreen(): List<TestItem> {
        return getTestItems(
            { deviceScreen ->
                SettinsScreen(
                    calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                    settingsUiState = SettingsUiState(
                        mixedPassword = SettingsItem.SettingsSwitch(
                            descriptionRes = Res.string.tryMixedPassword,
                            checked = true
                        ) {},
                        savePassword = SettingsItem.SettingsSwitch(
                            descriptionRes = Res.string.tryMixedPassword,
                            checked = false
                        ) {},
                    )
                )
            }, "settingsScreen"
        )
    }

    fun realOnStart(): List<TestItem> {
        val fakeAccountManager = FakeAccountManagerImpl()
        val viewModel = SettingsViewModel(
            moveToIntro = { },
            accountManager = fakeAccountManager
        )
        viewModel.onStart()
        return getTestItems({ deviceScreen ->
            SettinsScreen(
                calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                settingsUiState = viewModel.state.value
            )
        }, "real")
    }
}