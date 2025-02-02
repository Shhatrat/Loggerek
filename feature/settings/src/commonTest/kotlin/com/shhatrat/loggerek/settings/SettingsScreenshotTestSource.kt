package com.shhatrat.loggerek.settings

import androidx.compose.runtime.Composable
import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen
import com.shhatrat.loggerek.base.testing.TestItem
import loggerek.feature.settings.generated.resources.Res
import loggerek.feature.settings.generated.resources.tryMixedPassword

class SettingsScreenshotTestSource {

    fun settingsScreen(): List<TestItem> {
        return getProfileScreens(
            SettingsUiState(
                mixedPassword = SettingsItem.SettingsSwitch(descriptionRes = Res.string.tryMixedPassword, checked = true){},
                savePassword = SettingsItem.SettingsSwitch(descriptionRes = Res.string.tryMixedPassword, checked = false){},
            ), "settingsScreen"
        )
    }

    fun realOnStart(): List<TestItem> {
        val fakeAccountManager = FakeAccountManagerImpl()
        val viewModel = SettingsViewModel(
            moveToIntro = { },
            accountManager = fakeAccountManager
        )
        viewModel.onStart()
        return getProfileScreens(viewModel.state.value, "real")
    }

    private fun getProfileScreens(
        uiState: SettingsUiState,
        description: String
    ): List<TestItem> {
        return DeviceScreen.entries.map { deviceScreen ->
            TestItem(
                content = {
                    LoggerekTheme {
                        SettinsScreen(
                            calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                            settingsUiState = uiState
                        )
                    }
                },
                description = "${deviceScreen.name}-$description",
                width = deviceScreen.width,
                height = deviceScreen.height,
            )
        }
    }

}