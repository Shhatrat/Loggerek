package com.shhatrat.loggerek

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shhatrat.loggerek.api.di.apiModule
import com.shhatrat.loggerek.base.getTypography
import com.shhatrat.loggerek.di.PlatformSpecificModule
import com.shhatrat.loggerek.repository.di.repositoryModule
import loggerek.composeapp.generated.resources.Res
import loggerek.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication


@Composable
fun App(additionalKoinConfig: KoinApplication.() -> Unit = { }) {
    MaterialTheme(typography = getTypography()) {
        KoinApplication(application = {
            additionalKoinConfig.invoke(this)
            modules(repositoryModule, apiModule).modules(PlatformSpecificModule().getModules())
        }
        ) {
            content()
        }
    }
}

@Composable
private fun content() {
    var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}
