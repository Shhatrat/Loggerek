package com.shhatrat.loggerek.settings

import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen
import com.shhatrat.loggerek.base.testing.TestItem
import com.shhatrat.loggerek.base.testing.getTestItems

class ProfileScreenshotTestSource {

    fun provideProfileScreensWithUser(): List<TestItem> {
        return getTestItems({ deviceScreen ->
            ProfileScreen(
                calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                profileUiState = ProfileUiState(
                    user = FullUser.mock()
                )
            )
        }, "User")
    }

    fun provideProfileScreensWithError(): List<TestItem> {
        return getTestItems({ deviceScreen ->
            ProfileScreen(
                calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                profileUiState = ProfileUiState(
                    error = Error("Error")
                )
            )
        }, "Error")
    }

    private fun getProfileScreens(
        profileUiState: ProfileUiState,
        description: String
    ): List<TestItem> {
        return DeviceScreen.entries.map { deviceScreen ->
            TestItem(
                content = {
                    LoggerekTheme {
                        ProfileScreen(
                            calculateWindowSizeClass = { deviceScreen.getWindowSizeClass() },
                            profileUiState = profileUiState
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