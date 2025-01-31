package com.shhatrat.loggerek.profile

import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import com.shhatrat.loggerek.api.model.FullUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var fakeAccountManager: FakeAccountManagerImpl
    private lateinit var viewModel: ProfileViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeAccountManager = FakeAccountManagerImpl()
        viewModel = ProfileViewModel(fakeAccountManager)
    }

    @Test
    fun `initial state is set correctly`() = runTest {
        val state = viewModel.state.value

        assertNull(state.user)
        assertFalse(state.loader.active)
        assertNull(state.error)
    }

    @Test
    fun `onStart fetches user data successfully`() = runTest {
        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNotNull(state.user)
        assertEquals(FullUser.mock(), state.user)
        assertFalse(state.loader.active)
        assertNull(state.error)
    }

    @Test
    fun `onStart handles error correctly`() = runTest {
        fakeAccountManager.getFullUserDataHelper.doAfterDelayBeforeLogic = { throw Exception() }
        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNull(state.user)
        assertNotNull(state.error)
        assertFalse(state.loader.active)
    }

    @Test
    fun `loader activates during data fetch`() = runTest {
        fakeAccountManager.getFullUserDataHelper.delay = 1000L
        viewModel.onStart()
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