@file:OptIn(ExperimentalTestApi::class, ExperimentalMaterial3WindowSizeClassApi::class)

package com.shhatrat.loggerek

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import com.shhatrat.loggerek.account.di.fakeAccountModule
import com.shhatrat.loggerek.api.di.fakeApiModule
import com.shhatrat.loggerek.base.LoggerekTheme
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.di.PlatformSpecificModule
import com.shhatrat.loggerek.di.viewModelModule
import com.shhatrat.loggerek.intro.splash.IntroScreenTestTag
import com.shhatrat.loggerek.repository.Repository
import com.shhatrat.loggerek.repository.di.fakeRepositoryModule
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Test
    fun checkNavigationFromIntroToAuth() = runComposeUiTest {
        var navController: NavHostController? = null

        setContent {
            navController = rememberNavController()
            LoggerekTheme {
                AppNavigation(modifier = Modifier, navController = navController)
            }
        }
//        onNodeWithText("Rozpocznij...").performClick()
        onNodeWithTag(IntroScreenTestTag.buttonTag).performClick()
        waitForIdle()
        assert(navController!!.currentDestination?.route == AppDestinations.AUTH.name)
    }

    @After
    fun after(){
        stopKoin()
    }

    @Before
    fun before(){
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
    }

    @Test
    fun checkNavigationFromIntroToMain() = runComposeUiTest {
        var navController: NavHostController? = null
        val repository: Repository = getKoin().get()
        val accountManager: AccountManager = getKoin().get()
        (accountManager as? FakeAccountManagerImpl)?.isLogged = true
        sequenceOf(repository.token, repository.tokenSecret).forEach { it.save("token") }
        setContent {
            navController = rememberNavController()
            LoggerekTheme {
                AppNavigation(modifier = Modifier, navController = navController)
            }
        }
        waitForIdle()
        waitUntil(timeoutMillis = 5000) {
            navController!!.currentDestination?.route == AppDestinations.MAIN.name
        }
    }

}

