package com.shhatrat.loggerek

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shhatrat.loggerek.AppDestinations.*
import com.shhatrat.loggerek.account.di.accountModule
import com.shhatrat.loggerek.api.di.apiModule
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.di.PlatformSpecificModule
import com.shhatrat.loggerek.di.viewModelModule
import com.shhatrat.loggerek.main.MainScreen
import com.shhatrat.loggerek.intro.authorizate.AuthViewModel
import com.shhatrat.loggerek.intro.authorizate.AuthorizeScreen
import com.shhatrat.loggerek.intro.splash.IntroScreen
import com.shhatrat.loggerek.intro.splash.IntroViewModel
import com.shhatrat.loggerek.main.MainViewModel
import com.shhatrat.loggerek.repository.di.repositoryModule
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

@Composable
fun App(
    calculateWindowSizeClass: WindowSizeCallback,
    additionalKoinConfig: KoinApplication.() -> Unit = { }
) {
    LoggerekTheme {
        KoinApplication(application = {
            additionalKoinConfig.invoke(this)
            setupModules(calculateWindowSizeClass)
        }
        ) {
            AppNavigation(modifier = Modifier)
        }
    }
}

private fun KoinApplication.setupModules(calculateWindowSizeClass: WindowSizeCallback) {
    modules(repositoryModule, apiModule, accountModule, viewModelModule).modules(
        PlatformSpecificModule().getModules()
    ).modules(module {
        single<WindowSizeCallback> {
            { calculateWindowSizeClass() }
        }
    })
}

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = INTRO.name,
        modifier = modifier
    ) {
        entries.forEach {
            when (it) {
                INTRO -> composable(it.name) {
                    PrepareIntroScreen(
                        navigateToMain = { navController.nav(MAIN) },
                        navigateToAuth = { navController.nav(AUTH) })
                }

                MAIN -> composable(it.name) {
                    PrepareMainScreen(navigateToIntro = {
                        navController.nav(
                            INTRO
                        )
                    })
                }

                AUTH -> composable(it.name) {
                    PrepareAuthScreen(
                        navigateToMain = { navController.nav(MAIN) })
                }
            }
        }
    }
}

private fun NavController.nav(appDestinations: AppDestinations) {
    navigate(appDestinations.name)
}

@Composable
fun PrepareIntroScreen(navigateToMain: () -> Unit, navigateToAuth: () -> Unit) {
    val vm: IntroViewModel = koinViewModel { parametersOf(navigateToMain, navigateToAuth) }
    LaunchedEffect(Unit) { vm.onStart() }
    IntroScreen(koinInject<WindowSizeCallback>(), vm.state.collectAsState().value)
}

@Composable
fun PrepareMainScreen(navigateToIntro: () -> Unit) {
    val vm: MainViewModel = koinViewModel { parametersOf(navigateToIntro) }
    LaunchedEffect(Unit) { vm.onStart() }
    MainScreen(koinInject<WindowSizeCallback>(), vm.state.collectAsState().value)
}

@Composable
fun PrepareAuthScreen(navigateToMain: () -> Unit) {
    val vm: AuthViewModel = koinViewModel { parametersOf(navigateToMain) }
    LaunchedEffect(Unit) { vm.onStart() }
    AuthorizeScreen(koinInject<WindowSizeCallback>(), vm.state.collectAsState().value)
}
