package com.shhatrat.loggerek.account

/**
 * Interface representing the account management operations.
 */
interface AccountManager {

    /**
     * Object used to signal the completion of an authorization process.
     */
    object FinishCallback

    /**
     * Interface representing the response of an authorization process.
     */
    interface ProcessResponse {
        /**
         * The URL for the authorization process.
         */
        val url: String

        /**
         * Handles the user-provided PIN to complete the authorization process.
         *
         * @param pin The PIN provided by the user.
         * @return A [FinishCallback] signaling the completion of the process.
         */
        suspend fun pastePinAction(pin: String): FinishCallback
    }

    /**
     * Checks whether the user is currently logged in.
     *
     * @return `true` if the user is logged in, `false` otherwise.
     */
    suspend fun isUserLogged(): Boolean

    /**
     * Starts the authorization process.
     *
     * @return A [ProcessResponse] containing the details of the process.
     */
    suspend fun startAuthorizationProcess(): ProcessResponse
}