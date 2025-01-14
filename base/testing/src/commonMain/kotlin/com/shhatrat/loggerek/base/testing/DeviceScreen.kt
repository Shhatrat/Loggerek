package com.shhatrat.loggerek.base.testing

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import com.shhatrat.loggerek.base.testing.TestingHelper.getWindowSizeCompact
import com.shhatrat.loggerek.base.testing.TestingHelper.getWindowSizeExpanded

enum class DeviceScreen(val width: Int, val height: Int, val description: String) {
    ANDROID_PHONE(360, 800, "Android Phone"),
    ANDROID_TABLET(800, 1280, "Android Tablet"),
    IOS_PHONE(375, 812, "iOS Phone"),
    IOS_TABLET(834, 1112, "iOS Tablet"),
    DESKTOP(1440, 900, "Desktop"),
    WEB(1366, 768, "Web");

    fun getWindowSizeClass(): WindowSizeClass {
        return when {
            width < 600 -> getWindowSizeCompact()
            else -> getWindowSizeExpanded()
        }
    }
}