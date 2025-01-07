package com.shhatrat.loggerek.account

import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AccountManagerImpl(
    private val api: Api,
    private val repository: Repository
) : AccountManager {

    override suspend fun isUserLogged() = isTokenSaved() && canDownloadUsername()

    private fun isTokenSaved(): Boolean {
        return repository.token.get().isNullOrBlank().not() &&
                repository.tokenSecret.get().isNullOrBlank().not()
    }

    private suspend fun canDownloadUsername() =
        repository.token.get()
            ?.let { token -> repository.tokenSecret.get()
                ?.let { tokenSecret -> api.getLoggedUserNickname(token, tokenSecret).isNotEmpty() } } == true

    override suspend fun startAuthorizationProcess(): AccountManager.ProcessResponse {

        val requestTokenResponse = api.requestToken()

        return object : AccountManager.ProcessResponse {

            private var pastePinJob: Job? = null

            override val url: String = requestTokenResponse.url

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
}