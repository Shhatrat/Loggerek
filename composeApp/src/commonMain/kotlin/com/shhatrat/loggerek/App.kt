package com.shhatrat.loggerek

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shhatrat.loggerek.AppDestinations.AUTH
import com.shhatrat.loggerek.AppDestinations.INTRO
import com.shhatrat.loggerek.AppDestinations.MAIN
import com.shhatrat.loggerek.AppDestinations.entries
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.di.KoinHelper.setupBaseModules
import com.shhatrat.loggerek.di.KoinHelper.setupWindowSizeModules
import com.shhatrat.loggerek.intro.authorizate.AuthViewModel
import com.shhatrat.loggerek.intro.authorizate.AuthorizeScreen
import com.shhatrat.loggerek.intro.splash.IntroScreen
import com.shhatrat.loggerek.intro.splash.IntroViewModel
import com.shhatrat.loggerek.main.MainScreen
import com.shhatrat.loggerek.main.MainViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf

@Composable
fun App(
    calculateWindowSizeClass: WindowSizeCallback,
    additionalKoinConfig: KoinApplication.() -> Unit = { },
    startKoin: Boolean = true
) {
    LoggerekTheme {

        val shouldStartKoin = remember { mutableStateOf(startKoin) }
        if(shouldStartKoin.value){
            shouldStartKoin.value = false
            startKoin {
                additionalKoinConfig.invoke(this)
                setupBaseModules()
                setupWindowSizeModules(calculateWindowSizeClass)
            }
        }
        AppNavigation(modifier = Modifier)
    }
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = INTRO.name,
        modifier = modifier
    ) {
        entries.forEach {
            when (it) {
                INTRO -> composable(it.name) {
                    PrepareIntroScreen(
                        navigateToMain = {
                            navController.navWithoutStack(MAIN)
                        },
                        navigateToAuth = {
                            navController.navWithoutStack(AUTH)
                        })
                }

                MAIN -> composable(it.name) {
                    PrepareMainScreen(navigateToIntro = {
                        navController.navWithoutStack(
                            INTRO
                        )
                    })
                }

                AUTH -> composable(it.name) {
                    PrepareAuthScreen(
                        navigateToMain = { navController.navWithoutStack(MAIN) })
                }
            }
        }
    }
}

private fun NavController.navWithoutStack(appDestinations: AppDestinations) {
    navigate(appDestinations.name){
        popUpTo(0) { inclusive = true }
        launchSingleTop = true
    }
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
