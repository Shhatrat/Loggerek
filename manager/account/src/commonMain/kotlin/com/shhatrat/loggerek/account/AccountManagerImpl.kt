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

    override fun isUserLogged() = repository.token.get().isNullOrBlank().not() &&
                repository.tokenSecret.get().isNullOrBlank().not()

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