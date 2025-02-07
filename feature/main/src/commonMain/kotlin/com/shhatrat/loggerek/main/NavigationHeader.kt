@file:OptIn(InternalResourceApi::class)

package com.shhatrat.loggerek.main

import com.shhatrat.loggerek.base.OnBack
import loggerek.feature.main.generated.resources.Res
import loggerek.feature.main.generated.resources.headerLog
import loggerek.feature.main.generated.resources.headerProfile
import loggerek.feature.main.generated.resources.headerSettings
import loggerek.feature.main.generated.resources.headerWatch
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource

sealed class NavigationHeader(
    val imagePath: String,
    val nameRes: StringResource
) {
    sealed class Main(imagePath: String, headerLog: StringResource) :
        NavigationHeader(imagePath, headerLog) {
        data object PROFILE : Main("drawable/profile.svg", Res.string.headerProfile)
        data object CACHES : Main("drawable/checklist.svg", Res.string.headerLog)
        data object SETTINGS : Main("drawable/settings.svg", Res.string.headerSettings)
        companion object {
            fun getAll() = listOf(PROFILE, CACHES, SETTINGS)
        }
    }

    sealed class Specific(val onBack: OnBack, imagePath: String, headerLog: StringResource) :
        NavigationHeader(imagePath, headerLog) {
        class LOG(onBack: OnBack, val cacheId: String) :
            Specific(onBack, "drawable/checklist.svg", Res.string.headerLog)
        class WATCH(onBack: OnBack) :
            Specific(onBack, "drawable/watch.svg", Res.string.headerWatch)
    }
}