package com.shhatrat.loggerek.account

import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.model.FullUser
import kotlinx.coroutines.delay

/**
 * A fake implementation of the [AccountManager] interface for testing purposes.
 * Provides configurable behavior, delays, and state tracking for verification.
 *
 * @property isLogged Determines if the user is logged in.
 * @property responseUrl The URL returned during the authorization process.
 * @property onPastePinAction A lambda to handle the `pastePinAction` call.
 * @property isUserLoggedDelay The delay (in milliseconds) before the `isUserLogged` method completes.
 * @property startAuthorizationDelay The delay (in milliseconds) before the `startAuthorizationProcess` method completes.
 * @property pastePinDelay The delay (in milliseconds) before the `pastePinAction` completes.
 */
class FakeAccountManager(
    private var isLogged: Boolean = false,
    private var responseUrl: String = "http://example.com",
    var onPastePinAction: suspend (String) -> AccountManager.FinishCallback = { AccountManager.FinishCallback },
    var isUserLoggedDelay: Long = 0L,
    var startAuthorizationDelay: Long = 0L,
    var pastePinDelay: Long = 0L
) : AccountManager {

    /** Tracks if the `isUserLogged` method was called. */
    var isUserLoggedCalled = false

    /** Tracks if the `startAuthorizationProcess` method was called. */
    var startAuthorizationCalled = false

    /** Tracks if the `pastePinAction` method was called. */
    var pastePinCalled = false

    /** Stores the last PIN provided to `pastePinAction`. */
    var lastPin: String? = null

    /**
     * Simulates checking if the user is logged in.
     * This method can include a configurable delay for testing asynchronous behavior.
     *
     * @return A boolean indicating whether the user is logged in.
     */
    override suspend fun isUserLogged(): Boolean {
        isUserLoggedCalled = true
        delay(isUserLoggedDelay) // Simulate delay
        return isLogged
    }

    /**
     * Simulates starting the authorization process.
     * This method can include a configurable delay for testing asynchronous behavior.
     *
     * @return A [ProcessResponse] object containing the URL and the paste PIN action.
     */
    override suspend fun startAuthorizationProcess(): AccountManager.ProcessResponse {
        startAuthorizationCalled = true
        delay(startAuthorizationDelay) // Simulate delay
        return object : AccountManager.ProcessResponse {
            override val url: String = responseUrl
            override suspend fun pastePinAction(pin: String): AccountManager.FinishCallback {
                pastePinCalled = true
                lastPin = pin
                delay(pastePinDelay) // Simulate delay
                return onPastePinAction(pin)
            }
        }
    }

    override suspend fun getFullUserData(): FullUser {
        return FullUser.mock()
    }
}