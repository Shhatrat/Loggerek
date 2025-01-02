package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.OAuthLogic
import com.shhatrat.loggerek.api.oauth.OAuthLogic.buildOAuthHeader
import com.shhatrat.loggerek.api.oauth.OAuthLogic.generateSignature
import com.shhatrat.loggerek.api.oauth.OAuthParams
import com.shhatrat.loggerek.api.oauth.OAuthParams.getRequestTokenParams
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class ApiImpl(private val client: HttpClient) : Api {

    private suspend fun sendOAuthRequest(
        url: String,
        oauthParams: Map<String, String>,
        tokenSecret: String? = null
    ): String {
        val signature = generateSignature(
            method = "GET",
            url = url,
            params = oauthParams,
            consumerSecret = OpencachingApi.consumerSecret,
            tokenSecret = tokenSecret
        )
        val signedParams = oauthParams + ("oauth_signature" to signature)
        val response: HttpResponse = client.get(url) {
            headers {
                append("Authorization", buildOAuthHeader(signedParams))
            }
        }
        return response.bodyAsText()
    }

    override suspend fun requestToken(): OAuthRequestTokenResponse {
        val oauthParams = getRequestTokenParams()
        val responseBody = sendOAuthRequest(
            url = OpencachingApi.Url.requestToken(),
            oauthParams = oauthParams,
        )
        return OAuthLogic.parseRequestTokenResponse(responseBody)
    }

    override suspend fun accessToken(pin: String, token: String, tokenSecret: String): OAuthAccessTokenResponse {
        val oauthParams = OAuthParams.getAccessTokenParams(pin, token)
        val responseBody = sendOAuthRequest(
            url = OpencachingApi.Url.accessToken(),
            oauthParams = oauthParams,
            tokenSecret = tokenSecret
        )
        return OAuthLogic.parseAccessTokenResponse(responseBody)
    }

    override suspend fun cache(cacheId: String): String {
        return client.get(OpencachingApi.Url.geocache()) {
            parameter("consumer_key", OpencachingApi.consumerKey)
            parameter("cache_code", cacheId)
        }.bodyAsText()
    }
}