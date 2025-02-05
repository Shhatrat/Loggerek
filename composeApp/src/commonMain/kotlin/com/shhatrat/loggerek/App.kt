package com.shhatrat.loggerek

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.shhatrat.loggerek.account.di.accountModule
import com.shhatrat.loggerek.api.di.apiModule
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.base.browser.BrowserPlatformSpecificModule
import com.shhatrat.loggerek.di.PlatformSpecificModule
import com.shhatrat.loggerek.di.viewModelModule
import com.shhatrat.loggerek.intro.authorizate.AuthViewModel
import com.shhatrat.loggerek.intro.authorizate.AuthorizeScreen
import com.shhatrat.loggerek.intro.splash.IntroScreen
import com.shhatrat.loggerek.intro.splash.IntroViewModel
import com.shhatrat.loggerek.main.MainScreen
import com.shhatrat.loggerek.main.MainViewModel
import com.shhatrat.loggerek.manager.garmin.GarminManager
import com.shhatrat.loggerek.manager.garmin.GarminPlatformSpecificModule
import com.shhatrat.loggerek.manager.garmin.WatchCache
import com.shhatrat.loggerek.manager.garmin.WatchData
import com.shhatrat.loggerek.manager.garmin.WatchLog
import com.shhatrat.loggerek.manager.garmin.WatchSendKeys
import com.shhatrat.loggerek.manager.log.di.logManagerModule
import com.shhatrat.loggerek.repository.di.repositoryModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.getKoin
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
//            AppNavigation(modifier = Modifier)
            val garmin by getKoin().inject<GarminManager>()
            Column {
                Button(onClick = {
                    GlobalScope.launch(Dispatchers.Main) {
                        garmin.init()
                    }
                }) {
                    Text("init")
                }
                Button(onClick = {
                    GlobalScope.launch() {
                        garmin.sendData(
                            WatchSendKeys.GET_DATA(
                                watchData = WatchData(
                                    items = listOf(
                                        WatchCache("[OH] #6 Biblioteka", "OPE4EA"),
                                        WatchCache("Staw Schrödingera", "OPE40A"),
                                        WatchCache("Staw na ulicy Fiołkowej", "OPA00A"),
                                        WatchCache("GORUSZKA", "OPAEFA"),
                                        WatchCache("Cerkwiska i Cmentarze -Smerek", "OPA34D"),
                                    ),
                                    logs = listOf(
                                        WatchLog("Wszystko gra", "1", "found"),
                                        WatchLog("super skrzyneczka", "2", "found"),
                                        WatchLog("cos tam", "3", "comment"),
                                        WatchLog("ni ma", "4", "not found")
                                    )
                                )
                            )
                        )
                    }
                }) {
                    Text("sendData")
                }
                Button(onClick = {
                    GlobalScope.launch() {
                        garmin.getData().collect {
                            println(it)
                        }
                    }
                }) {
                    Text("getData")
                }
            }
        }
    }
}

private fun KoinApplication.setupModules(calculateWindowSizeClass: WindowSizeCallback) {
    modules(repositoryModule, apiModule, accountModule, logManagerModule, viewModelModule).modules(
        PlatformSpecificModule().getModules().plus(
            BrowserPlatformSpecificModule().getModules()
        ).plus(
            GarminPlatformSpecificModule().getModules()
        )
    ).modules(module {
        single<WindowSizeCallback> {
            { calculateWindowSizeClass() }
        }
    })
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
                            navController.nav(MAIN)
                        },
                        navigateToAuth = {
                            navController.nav(AUTH)
                        })
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
