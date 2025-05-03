package com.shhatrat.loggerek.intro.splash

import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class IntroViewModelTest {
    private lateinit var fakeAccountManager: FakeAccountManagerImpl
    private lateinit var viewModel: IntroViewModel

    private val testDispatcher = StandardTestDispatcher()

    private var navigateToMainScreenCalled = false
    private var navigateToAuthScreenCalled = false

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeAccountManager =
            FakeAccountManagerImpl(
                isLogged = false,
            )

        navigateToMainScreenCalled = false
        navigateToAuthScreenCalled = false

        viewModel =
            IntroViewModel(
                navigateToMainScreen = { navigateToMainScreenCalled = true },
                navigateToAuthScreen = { navigateToAuthScreenCalled = true },
                accountManager = fakeAccountManager,
            )
    }

    @Test
    fun initialStateShouldHaveLoaderActive() =
        runTest {
            val state = viewModel.state.value
            assertTrue(state.loader.active)
            assertNull(state.buttonAction)
        }

    @Test
    fun ifUserIsLoggedInShouldNavigateToMainScreen() =
        runTest {
            fakeAccountManager.isLogged = true

            viewModel.onStart()
            testDispatcher.scheduler.advanceUntilIdle()

            assertTrue(navigateToMainScreenCalled)
            assertFalse(navigateToAuthScreenCalled)
        }

    @Test
    fun ifUserIsNotLoggedInShouldShowLoginButton() =
        runTest {
            fakeAccountManager.isLogged = false

            viewModel.onStart()
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertNotNull(state.buttonAction)
            assertFalse(navigateToMainScreenCalled)
            assertFalse(navigateToAuthScreenCalled)
        }

    @Test
    fun clickingLoginButtonShouldNavigateToAuthScreen() =
        runTest {
            fakeAccountManager.isLogged = false

            viewModel.onStart()
            testDispatcher.scheduler.advanceUntilIdle()

            viewModel.state.value.buttonAction
                ?.invoke()

            assertTrue(navigateToAuthScreenCalled)
            assertFalse(navigateToMainScreenCalled)
        }

    @Test
    fun loaderShouldBeActiveWhileCheckingLoginState() =
        runTest {
            fakeAccountManager.isUserLoggedHelper.delay = 1000L

            viewModel.onStart()
            testDispatcher.scheduler.advanceTimeBy(500)

            assertTrue(viewModel.state.value.loader.active)

            testDispatcher.scheduler.advanceUntilIdle()

            assertFalse(viewModel.state.value.loader.active)
        }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
