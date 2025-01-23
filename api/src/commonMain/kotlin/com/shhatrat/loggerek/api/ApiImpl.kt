package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.OpencachingParam
import com.shhatrat.loggerek.api.model.OpencachingParam.Companion.parseForApiNotFormatted
import com.shhatrat.loggerek.api.model.UserName
import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.OAuthLogic
import com.shhatrat.loggerek.api.oauth.OAuthLogic.buildOAuthHeader
import com.shhatrat.loggerek.api.oauth.OAuthLogic.generateSignature
import com.shhatrat.loggerek.api.oauth.OAuthParams
import com.shhatrat.loggerek.api.oauth.OAuthParams.generateOAuthNonce
import com.shhatrat.loggerek.api.oauth.OAuthParams.getRequestTokenParams
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

class ApiImpl(private val client: HttpClient) : Api {

    private suspend fun sendOAuthRequest(
        url: String,
        oauthParams: Map<String, String>,
        tokenSecret: String? = ""
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
        val g =  client.get(OpencachingApi.Url.geocache()) {
            parameter("consumer_key", OpencachingApi.consumerKey)
            parameter("cache_code", cacheId)
            parameter("fields", parseForApiNotFormatted(OpencachingParam.Geocache.getAll()))
        }.body<Geocache>()
        return g.name
    }

    override suspend fun getLoggedUserNickname(token: String, tokenSecret: String): UserName {
        val params = listOf(OpencachingParam.User.USERNAME)
        return getUser(client, token, tokenSecret, params)
    }

    override suspend fun getLoggedUserData(token: String, tokenSecret: String): FullUser {
        return getUser(client, token, tokenSecret, OpencachingParam.User.getAll())
    }

    override suspend fun getFullCache(token: String, tokenSecret: String): Geocache {
        return getCache(client, token, tokenSecret, OpencachingParam.Geocache.getAll())
    }
}