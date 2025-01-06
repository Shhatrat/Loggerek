package com.shhatrat.loggerek

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.shhatrat.loggerek.intro.IntroScreen
import loggerek.composeapp.generated.resources.Res
import loggerek.composeapp.generated.resources.destination_intro
import loggerek.composeapp.generated.resources.destination_main
import org.jetbrains.compose.resources.StringResource

enum class AppDestinations(val nameRes: StringResource,
                           val openAction: @Composable () -> @Composable Unit) {
    INTRO(Res.string.destination_intro, { IntroScreen() }),
    MAIN(Res.string.destination_main, { Text("") })
}