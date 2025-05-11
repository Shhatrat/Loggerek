package com.shhatrat.loggerek.settings

import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.model.FullUser
import dev.mokkery.answering.calls
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

class ProfileViewModelMockkTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun initialStateIsSetCorrectly() = runTest {
        lateinit var viewModel : ProfileViewModel
        val accountManager = mock<AccountManager>(){
            everySuspend { getFullUserData() } calls { FullUser.mock() }
        }
        viewModel = ProfileViewModel(accountManager)

        val state = viewModel.state.value
        assertNull(state.user)
        assertFalse(state.loader.active)
        assertNull(state.error)
    }

    @Test
    fun onStartFetchesUserDataSuccessfully() = runTest {
        lateinit var viewModel : ProfileViewModel
        val accountManager = mock<AccountManager>(){
            everySuspend { getFullUserData() } calls { FullUser.mock() }
        }
        viewModel = ProfileViewModel(accountManager)

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
        lateinit var viewModel : ProfileViewModel
        val accountManager = mock<AccountManager>(){
            everySuspend { getFullUserData() } calls { throw Exception() }
        }
        viewModel = ProfileViewModel(accountManager)
        viewModel.onStart()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertNull(state.user)
        assertNotNull(state.error)
        assertFalse(state.loader.active)
    }

    @Test
    fun loaderActivatesDuringDataFetch() = runTest {

        lateinit var viewModel : ProfileViewModel
        val accountManager = mock<AccountManager>(){
            everySuspend { getFullUserData() } calls {
                delay(1000)
                FullUser.mock()
            }
        }
        viewModel = ProfileViewModel(accountManager)
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