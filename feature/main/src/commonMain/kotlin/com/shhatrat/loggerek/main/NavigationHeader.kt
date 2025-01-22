@file:OptIn(InternalResourceApi::class)

package com.shhatrat.loggerek.main

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

enum class NavigationHeader(
    val icon: DrawableResource,
    val nameRes: StringResource
) {
    PROFILE(Res.drawable.profile, Res.string.headerProfile),
    LOG(Res.drawable.checklist, Res.string.headerLog),
    WATCH(Res.drawable.watch, Res.string.headerWatch),
    SETTINGS(Res.drawable.settings, Res.string.headerSettings),
}