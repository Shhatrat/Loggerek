package com.shhatrat.loggerek.settings

import com.shhatrat.loggerek.account.FakeAccountManagerImpl
import com.shhatrat.loggerek.api.model.FullUser
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
    fun initialStateIsSetCorrectly() = runTest {
        val state = viewModel.state.value

        assertNull(state.user)
        assertFalse(state.loader.active)
        assertNull(state.error)
    }

    @Test
    fun onStartFetchesUserDataSuccessfully() = runTest {
        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNotNull(state.user)
        assertEquals(FullUser.mock(), state.user)
        assertFalse(state.loader.active)
        assertNull(state.error)
    }

    @Test
    fun onStartHandlesErrorCorrectly() = runTest {
        fakeAccountManager.getFullUserDataHelper.doAfterDelayBeforeLogic = { throw Exception() }
        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNull(state.user)
        assertNotNull(state.error)
        assertFalse(state.loader.active)
    }

    @Test
    fun loaderActivatesDuringDataFetch() = runTest {
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