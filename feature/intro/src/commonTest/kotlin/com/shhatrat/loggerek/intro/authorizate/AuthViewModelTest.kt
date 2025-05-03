package com.shhatrat.loggerek.intro.authorizate

import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import com.shhatrat.loggerek.base.browser.IBrowserUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {
    private lateinit var fakeAccountManager: FakeAccountManagerImpl
    private lateinit var viewModel: AuthViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeAccountManager =
            FakeAccountManagerImpl(
                isLogged = false,
                responseUrl = "http://example.com",
            )
        viewModel =
            AuthViewModel(
                navigateToMain = { /* No-op */ },
                accountManager = fakeAccountManager,
                browserUtil =
                    object : IBrowserUtil {
                        override fun openWithUrl(url: String) {}
                    },
            )
    }

    @Test
    fun initialStateIsSetCorrectly() =
        runTest {
            viewModel.onStart()
            val state = viewModel.state.value

            assertFalse(state.loader.active)
            assertNotNull(state.startButton)
            assertNull(state.error)
            assertNull(state.browserLink)
            assertNull(state.pastePinAction)
            assertNull(state.openBrowserAction)
        }

    @Test
    fun startActionUpdatesStateOnSuccess() =
        runTest {
            viewModel.onStart()

            // Simulate start button click
            viewModel.state.value.startButton
                ?.invoke()
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertFalse(state.loader.active)
            assertEquals("http://example.com", state.browserLink)
            assertNotNull(state.openBrowserAction)
            assertNotNull(state.pastePinAction)
            assertNull(state.startButton)
        }

    @Test
    fun pastePinActionUpdatesStateOnSuccess() =
        runTest {
            viewModel.onStart()

            // Simulate start button click
            viewModel.state.value.startButton
                ?.invoke()
            testDispatcher.scheduler.advanceUntilIdle()

            // Simulate paste PIN action
            viewModel.state.value.pastePinAction
                ?.invoke("1234")
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertNull(state.error)
            assertNull(state.browserLink)
            assertNull(state.openBrowserAction)
            assertNull(state.pastePinAction)
        }

    @Test
    fun pastePinActionHandlesErrorAndResetsState() =
        runTest {
            fakeAccountManager.onPastePinAction = { throw RuntimeException("Invalid PIN") }
            viewModel.onStart()

            // Simulate start button click
            viewModel.state.value.startButton
                ?.invoke()
            testDispatcher.scheduler.advanceUntilIdle()

            // Simulate paste PIN action
            viewModel.state.value.pastePinAction
                ?.invoke("1234")
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.state.value
            assertNotNull(state.error)
            assertNull(state.browserLink)
            assertNull(state.openBrowserAction)
            assertNull(state.pastePinAction)
            assertNotNull(state.startButton)
        }

    @Test
    fun loaderActivatesDuringStartButtonClick() =
        runTest {
            fakeAccountManager.onPastePinAction = { throw RuntimeException("Invalid PIN") }
            fakeAccountManager.startAuthorizationProcessHelper.delay = 1000L
            viewModel.onStart()

            viewModel.state.value.startButton
                ?.invoke()
            testDispatcher.scheduler.advanceTimeBy(100)

            assertTrue(viewModel.state.value.loader.active)
            testDispatcher.scheduler.advanceUntilIdle()
            assertFalse(viewModel.state.value.loader.active)
        }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
