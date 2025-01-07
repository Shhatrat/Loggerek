package com.shhatrat.loggerek

import loggerek.composeapp.generated.resources.Res
import loggerek.composeapp.generated.resources.destination_intro
import loggerek.composeapp.generated.resources.destination_main
import org.jetbrains.compose.resources.StringResource

enum class AppDestinations(
    val nameRes: StringResource,
) {
    INTRO(Res.string.destination_intro),
    MAIN(Res.string.destination_main),
    AUTH(Res.string.destination_main),
}