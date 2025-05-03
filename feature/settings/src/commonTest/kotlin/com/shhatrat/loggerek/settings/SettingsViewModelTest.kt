package com.shhatrat.loggerek.settings

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
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private lateinit var fakeAccountManager: FakeAccountManagerImpl
    private lateinit var viewModel: SettingsViewModel
    private var navigateToIntroCalled = false

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        fakeAccountManager = FakeAccountManagerImpl()
        viewModel = SettingsViewModel(
            moveToIntro = { navigateToIntroCalled = true },
            accountManager = fakeAccountManager,
            moveToWatch = { },
            watchManager = null
        )
    }

    @Test
    fun `initial state is set correctly`() = runTest {
        viewModel.onStart()
        val state = viewModel.state.value

        assertNotNull(state.savePassword)
        assertNotNull(state.mixedPassword)
        assertNotNull(state.logout)

        assertEquals(true, state.savePassword?.checked)
        assertEquals(true, state.mixedPassword?.checked)
        assertNull(state.error)
        assertFalse(state.loader.active)
    }

    @Test
    fun `toggling savePassword updates state and accountManager`() = runTest {
        viewModel.onStart()

        // Toggle savePassword to false
        viewModel.state.value.savePassword?.onChecked?.invoke(false)
        assertFalse(viewModel.state.value.savePassword?.checked ?: true)
        assertFalse(fakeAccountManager.savePassword)

        // Toggle savePassword to true
        viewModel.state.value.savePassword?.onChecked?.invoke(true)
        assertTrue(viewModel.state.value.savePassword?.checked ?: false)
        assertTrue(fakeAccountManager.savePassword)
    }

    @Test
    fun `toggling mixedPassword updates state and accountManager`() = runTest {
        viewModel.onStart()

        // Toggle mixedPassword to false
        viewModel.state.value.mixedPassword?.onChecked?.invoke(false)
        assertFalse(viewModel.state.value.mixedPassword?.checked ?: true)
        assertFalse(fakeAccountManager.tryMixedPassword)

        // Toggle mixedPassword to true
        viewModel.state.value.mixedPassword?.onChecked?.invoke(true)
        assertTrue(viewModel.state.value.mixedPassword?.checked ?: false)
        assertTrue(fakeAccountManager.tryMixedPassword)
    }

    @Test
    fun `logout button calls accountManager logout and navigates to intro`() = runTest {
        viewModel.onStart()

        assertFalse(navigateToIntroCalled)
        viewModel.state.value.logout?.action?.invoke()

        assertTrue(fakeAccountManager.logoutHelper.isInvoked)
        assertTrue(navigateToIntroCalled)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }
}