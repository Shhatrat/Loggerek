@file:OptIn(InternalResourceApi::class)

package com.shhatrat.loggerek.main

import com.shhatrat.loggerek.base.OnBack
import loggerek.feature.main.generated.resources.Res
import loggerek.feature.main.generated.resources.checklist
import loggerek.feature.main.generated.resources.headerLog
import loggerek.feature.main.generated.resources.headerProfile
import loggerek.feature.main.generated.resources.headerSettings
import loggerek.feature.main.generated.resources.headerWatch
import loggerek.feature.main.generated.resources.profile
import loggerek.feature.main.generated.resources.settings
import loggerek.feature.main.generated.resources.watch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource

sealed class NavigationHeader(
    val icon: DrawableResource,
    val nameRes: StringResource
) {
    sealed class Main(checklist: DrawableResource, headerLog: StringResource): NavigationHeader(checklist, headerLog){
        data object PROFILE:  Main(Res.drawable.profile, Res.string.headerProfile)
        data object WATCH:  Main(Res.drawable.watch, Res.string.headerWatch)
        data object CACHES:  Main(Res.drawable.watch, Res.string.headerWatch)
        data object SETTINGS:  Main(Res.drawable.settings, Res.string.headerSettings)
        companion object {
            fun getAll() = listOf(PROFILE, CACHES, WATCH, SETTINGS)
        }
    }

    sealed class Specific(val onBack: OnBack, checklist: DrawableResource, headerLog: StringResource) : NavigationHeader(checklist, headerLog){
        class LOG(onBack: OnBack, val cacheId: String):  Specific(onBack, Res.drawable.checklist, Res.string.headerLog)
    }
}