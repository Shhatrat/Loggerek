package com.shhatrat.loggerek.intro.splash

import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

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

        fakeAccountManager = FakeAccountManagerImpl(
            isLogged = false
        )

        navigateToMainScreenCalled = false
        navigateToAuthScreenCalled = false

        viewModel = IntroViewModel(
            navigateToMainScreen = { navigateToMainScreenCalled = true },
            navigateToAuthScreen = { navigateToAuthScreenCalled = true },
            accountManager = fakeAccountManager
        )
    }

    @Test
    fun `initial state should have loader active`() = runTest {
        val state = viewModel.state.value
        assertTrue(state.loader.active)
        assertNull(state.buttonAction)
    }

    @Test
    fun `if user is logged in should navigate to main screen`() = runTest {
        fakeAccountManager.isLogged = true

        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(navigateToMainScreenCalled)
        assertFalse(navigateToAuthScreenCalled)
    }

    @Test
    fun `if user is not logged in should show login button`() = runTest {
        fakeAccountManager.isLogged = false

        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNotNull(state.buttonAction)
        assertFalse(navigateToMainScreenCalled)
        assertFalse(navigateToAuthScreenCalled)
    }

    @Test
    fun `clicking login button should navigate to auth screen`() = runTest {
        fakeAccountManager.isLogged = false

        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.value.buttonAction?.invoke()

        assertTrue(navigateToAuthScreenCalled)
        assertFalse(navigateToMainScreenCalled)
    }

    @Test
    fun `loader should be active while checking login state`() = runTest {
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