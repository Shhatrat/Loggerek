package com.shhatrat.loggerek

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shhatrat.loggerek.api.di.apiModule
import com.shhatrat.loggerek.base.getTypography
import com.shhatrat.loggerek.di.PlatformSpecificModule
import com.shhatrat.loggerek.intro.IntroScreen
import com.shhatrat.loggerek.repository.di.repositoryModule
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
            AppNavigation(modifier = Modifier)
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INTRO.name,
        modifier = modifier
    ) {
        AppDestinations.entries.forEach { entry ->
            composable(entry.name) { entry.openAction() }
        }
    }
}
