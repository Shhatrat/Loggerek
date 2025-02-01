package com.shhatrat.loggerek.account

import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.repository.ReadRepositoryDelegate
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * The implementation of the [AccountManager] interface responsible for managing user authentication,
 * authorization, and user data retrieval. It interacts with the [Api] for network requests and the
 * [Repository] for storing and retrieving authentication tokens and user preferences.
 *
 * ## Key Features:
 * - **Login Status Check**: Verifies if the user is logged in by checking the presence of valid tokens
 *   and the ability to fetch the user's username.
 * - **Authorization Process**: Handles the OAuth flow, including requesting a token, redirecting the user
 *   to the authorization URL, and exchanging the PIN for access tokens.
 * - **User Data Retrieval**: Fetches the full user data using valid access tokens.
 * - **Logout**: Clears stored tokens, effectively logging the user out.
 * - **Password Management**: Supports saving passwords and attempting mixed passwords based on user preferences.
 *
 * @property api The [Api] instance used for making network requests.
 * @property repository The [Repository] instance used for storing and retrieving tokens and preferences.
 */
class AccountManagerImpl(
    private val api: Api,
    private val repository: Repository
) : AccountManager {

    /**
     * Checks if the user is currently logged in.
     *
     * @return `true` if valid tokens are stored and the username can be fetched, otherwise `false`.
     */
    override suspend fun isUserLogged() = isTokenSaved() && canDownloadUsername()

    /**
     * Checks if valid tokens are stored in the repository.
     *
     * @return `true` if both the token and token secret are present and non-blank, otherwise `false`.
     */
    private fun isTokenSaved(): Boolean {
        return repository.token.get().isNullOrBlank().not() &&
                repository.tokenSecret.get().isNullOrBlank().not()
    }

    /**
     * Verifies if the username can be fetched using the stored tokens.
     *
     * @return `true` if the username is successfully fetched and non-empty, otherwise `false`.
     */
    private suspend fun canDownloadUsername() =
        repository.token.get()
            ?.let { token ->
                repository.tokenSecret.get()
                    ?.let { tokenSecret ->
                        api.getLoggedUserNickname(
                            token,
                            tokenSecret
                        ).username.isNotEmpty()
                    }
            } == true

    /**
     * Initiates the OAuth authorization process.
     *
     * @return A [ProcessResponse] object containing:
     *   - The authorization URL (`url`) to redirect the user.
     *   - A suspendable function `pastePinAction(pin: String)` to exchange the PIN for access tokens.
     */
    override suspend fun startAuthorizationProcess(): AccountManager.ProcessResponse {

        val requestTokenResponse = api.requestToken()

        return object : AccountManager.ProcessResponse {

            private var pastePinJob: Job? = null

            override val url: String = requestTokenResponse.url

            /**
             * Exchanges the provided PIN for access tokens and stores them in the repository.
             *
             * @param pin The PIN obtained from the user after authorization.
             * @return A [FinishCallback] indicating the completion of the process.
             */
            override suspend fun pastePinAction(pin: String): AccountManager.FinishCallback {
                return coroutineScope {
                    pastePinJob?.cancelAndJoin()
                    pastePinJob = launch {
                        val response = api.accessToken(
                            pin,
                            requestTokenResponse.token,
                            requestTokenResponse.tokenSecret
                        )
                        repository.token.save(response.oauthToken)
                        repository.tokenSecret.save(response.oauthTokenSecret)
                    }
                    pastePinJob?.join()
                    AccountManager.FinishCallback
                }
            }
        }
    }

    /**
     * Logs out the user by removing stored tokens from the repository.
     */
    override fun logout() {
        repository.token.remove()
        repository.tokenSecret.remove()
    }

    /**
     * Fetches the full user data using the stored tokens.
     *
     * @return A [FullUser] object containing the user's data.
     */
    override suspend fun getFullUserData(): FullUser {
        val t = repository.safeTokenAndTokenSecret()
        return api.getLoggedUserData(t.token, t.tokenSecret)
    }

    /**
     * Determines whether mixed password attempts are allowed.
     * This property is delegated to the [repository.tryMixedPassword] preference.
     */
    override var tryMixedPassword: Boolean by ReadRepositoryDelegate(repository.tryMixedPassword)

    /**
     * Determines whether the password should be saved to caches notes.
     * This property is delegated to the [repository.savePassword] preference.
     */
    override var savePassword: Boolean by ReadRepositoryDelegate(repository.savePassword)
}