@file:OptIn(ExperimentalTestApi::class, ExperimentalMaterial3WindowSizeClassApi::class)

package com.shhatrat.loggerek

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shhatrat.loggerek.account.di.fakeAccountModule
import com.shhatrat.loggerek.api.di.fakeApiModule
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.di.PlatformSpecificModule
import com.shhatrat.loggerek.di.viewModelModule
import com.shhatrat.loggerek.repository.di.fakeRepositoryModule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Test
    fun checkNavigationFromIntroToMain() = runComposeUiTest {
        startKoin {
            modules(
                fakeRepositoryModule,
                fakeApiModule,
                fakeAccountModule,
                viewModelModule
            ).modules(
                PlatformSpecificModule().getModules()
            ).modules(module {
                single<WindowSizeCallback> {
                    { WindowSizeClass.calculateFromSize(DpSize(0.dp, 0.dp)) }
                }
            })
        }
        var navController: NavHostController? = null

        setContent {
            navController = rememberNavController()
            LoggerekTheme {
                AppNavigation(modifier = Modifier, navController = navController)
            }
        }
            onNodeWithText("Rozpocznij...").performClick()
            waitForIdle()
            onNodeWithText("Zaraz rozpocznie się autoryzacja, Twoje konto Opencaching zostanie połączone z aplikacją. W każdej chwili można je odłączyć.").assertExists()
        assert(navController!!.currentDestination?.route == AppDestinations.AUTH.name)
    }
}

