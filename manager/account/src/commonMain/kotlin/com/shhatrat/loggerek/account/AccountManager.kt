package com.shhatrat.loggerek.account

import com.shhatrat.loggerek.api.model.FullUser

/**
 * Interface defining the contract for account management operations.
 * This includes user authentication, authorization, user data retrieval, and logout functionality.
 *
 * ## Key Responsibilities:
 * - **Login Status Check**: Verifies if the user is currently logged in.
 * - **Authorization Process**: Manages the OAuth flow, including starting the process and handling PIN input.
 * - **User Data Retrieval**: Fetches the full user data for the logged-in user.
 * - **Logout**: Clears user session data, effectively logging the user out.
 * - **Password Management**: Allows configuration of password-saving behavior and mixed password attempts.
 */
interface AccountManager {

    /**
     * Object used to signal the successful completion of an authorization process.
     * This is returned after the PIN is successfully processed.
     */
    object FinishCallback

    /**
     * Interface representing the response of an authorization process.
     * It provides the necessary details to complete the OAuth flow.
     */
    interface ProcessResponse {
        /**
         * The URL to which the user should be redirected to authorize the application.
         * This is typically the OAuth provider's authorization page.
         */
        val url: String

        /**
         * Handles the user-provided PIN to complete the authorization process.
         * This method exchanges the PIN for access tokens and finalizes the login process.
         *
         * @param pin The PIN provided by the user after authorization.
         * @return A [FinishCallback] signaling the successful completion of the process.
         */
        suspend fun pastePinAction(pin: String): FinishCallback
    }

    /**
     * Checks whether the user is currently logged in.
     * This typically involves verifying the presence and validity of stored authentication tokens.
     *
     * @return `true` if the user is logged in, `false` otherwise.
     */
    suspend fun isUserLogged(): Boolean

    /**
     * Starts the OAuth authorization process.
     * This method initiates the process by requesting a token and providing the authorization URL.
     *
     * @return A [ProcessResponse] containing the authorization URL and a method to handle the PIN input.
     */
    suspend fun startAuthorizationProcess(): ProcessResponse

    /**
     * Fetches the full user data for the currently logged-in user.
     * This typically includes details such as username, email, and other profile information.
     *
     * @return A [FullUser] object containing the user's data.
     */
    suspend fun getFullUserData(): FullUser

    /**
     * Logs out the user by clearing stored authentication tokens and session data.
     * This effectively ends the user's session.
     */
    fun logout()

    /**
     * Determines whether the user's password should be saved to cache notes.
     */
    var savePassword: Boolean

    /**
     * Determines whether mixed password attempts are allowed.
     * This property enables or disables the ability to try variations of the user's password
     */
    var tryMixedPassword: Boolean
}