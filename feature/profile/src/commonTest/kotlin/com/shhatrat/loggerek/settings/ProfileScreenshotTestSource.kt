package com.shhatrat.loggerek.settings

import androidx.compose.runtime.Composable
import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.base.Error
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.testing.DeviceScreen

class ProfileScreenshotTestSource {

    data class TestItem(
        val content: @Composable () -> Unit,
        val description: String,
        val width: Int,
        val height: Int,
    )

    fun provideProfileScreensWithUser(): List<TestItem> {
        return getProfileScreens(
            ProfileUiState(
                user = FullUser.mock()
            ), "User"
        )
    }

    fun provideProfileScreensWithError(): List<TestItem> {
        return getProfileScreens(
            ProfileUiState(
                error = Error("Error")
            ), "Error"
        )
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