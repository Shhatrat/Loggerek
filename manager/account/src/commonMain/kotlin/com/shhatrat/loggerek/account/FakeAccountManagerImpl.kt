package com.shhatrat.loggerek.account

import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.base.testing.FunctionHelper
import com.shhatrat.loggerek.base.testing.SuspendFunctionHelper
import kotlinx.coroutines.delay

/**
 * A fake implementation of the [AccountManager] interface for testing purposes.
 * It allows simulating various authentication scenarios, including login status checks,
 * authorization processes, and user data retrieval, with configurable delays and hooks
 * for verification.
 *
 * ## Features:
 * - Simulates checking whether a user is logged in.
 * - Mocks the authorization process with a fake response URL.
 * - Provides an overridable callback for handling PIN input during authentication.
 * - Supports configurable delays for testing asynchronous behavior.
 * - Tracks method calls for verification in unit tests.
 * - Allows configuration of password-saving behavior.
 * - Supports mixed password attempts for testing purposes.
 *
 * @property logoutHelper Tracks and configures the behavior of the `logout()` function.
 * @property getFullUserDataHelper Tracks and configures the behavior of the `getFullUserData()` function.
 * @property startAuthorizationProcessHelper Tracks and configures the behavior of the `startAuthorizationProcess()` function.
 * @property pastePinActionHelper Tracks and configures the behavior of the `pastePinAction()` function.
 * @property isUserLoggedHelper Tracks and configures the behavior of the `isUserLogged()` function.
 * @property isLogged Determines whether the user is currently logged in.
 * @property responseUrl The URL returned during the authorization process.
 * @property onPastePinAction A lambda function that handles the behavior when a PIN is entered.
 * @property savePassword Determines whether the password should be saved. Default is `true`.
 * @property tryMixedPassword Determines whether mixed password attempts should be allowed. Default is `true`.
 */
class FakeAccountManagerImpl(

    val logoutHelper: FunctionHelper = FunctionHelper(),
    val getFullUserDataHelper: SuspendFunctionHelper = SuspendFunctionHelper(),
    val startAuthorizationProcessHelper: SuspendFunctionHelper = SuspendFunctionHelper(),
    val pastePinActionHelper: SuspendFunctionHelper = SuspendFunctionHelper(),
    val isUserLoggedHelper: SuspendFunctionHelper = SuspendFunctionHelper(),

    var isLogged: Boolean = false,
    var responseUrl: String = "http://example.com",
    var onPastePinAction: suspend (String) -> AccountManager.FinishCallback = { AccountManager.FinishCallback },
) : AccountManager {

    /** Stores the last PIN provided to `pastePinAction`. */
    var lastPin: String? = null

    /**
     * Simulates checking if the user is logged in.
     * Can be configured with a delay using `isUserLoggedHelper.delay` for testing async behavior.
     *
     * @return `true` if the user is logged in, otherwise `false`.
     */
    override suspend fun isUserLogged(): Boolean {
        isUserLoggedHelper.isInvoked = true
        delay(isUserLoggedHelper.delay)
        return isLogged
    }

    /**
     * Simulates starting the authorization process, returning a mock URL and a paste PIN action.
     * The execution delay can be controlled via `startAuthorizationProcessHelper.delay`.
     *
     * @return A [ProcessResponse] object containing:
     *   - The authorization URL (`responseUrl`).
     *   - A suspendable function `pastePinAction(pin: String)`, which simulates entering a PIN.
     */
    override suspend fun startAuthorizationProcess(): AccountManager.ProcessResponse {
        startAuthorizationProcessHelper.isInvoked = true
        delay(startAuthorizationProcessHelper.delay)
        return object : AccountManager.ProcessResponse {
            override val url: String = responseUrl
            override suspend fun pastePinAction(pin: String): AccountManager.FinishCallback {
                pastePinActionHelper.isInvoked = true
                delay(pastePinActionHelper.delay)
                lastPin = pin
                return onPastePinAction(pin)
            }
        }
    }

    /**
     * Simulates logging out the user.
     * The execution delay can be controlled via `logoutHelper.doAfterDelayBeforeLogic()`.
     */
    override fun logout() {
        logoutHelper.isInvoked = true
        logoutHelper.doAfterDelayBeforeLogic()
    }

    /**
     * Simulates fetching the full user data.
     * The execution delay can be controlled via `getFullUserDataHelper.delay`.
     *
     * @return A mock [FullUser] object.
     */
    override suspend fun getFullUserData(): FullUser {
        getFullUserDataHelper.isInvoked = true
        delay(getFullUserDataHelper.delay)
        getFullUserDataHelper.doAfterDelayBeforeLogic()
        return FullUser.mock()
    }

    /**
     * Determines whether the password should be saved.
     * This property can be used to simulate different password-saving behaviors in tests.
     */
    override var savePassword: Boolean = true

    /**
     * Determines whether mixed password attempts should be allowed.
     * This property can be used to simulate scenarios where mixed password attempts are enabled or disabled.
     */
    override var tryMixedPassword: Boolean = true
}