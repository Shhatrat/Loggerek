package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogResponse
import com.shhatrat.loggerek.api.model.LogTypeResponse
import com.shhatrat.loggerek.api.model.OpencachingParam
import com.shhatrat.loggerek.api.model.OpencachingParam.Companion.parseForApiNotFormatted
import com.shhatrat.loggerek.api.model.SearchResponse
import com.shhatrat.loggerek.api.model.SubmitLogData
import com.shhatrat.loggerek.api.model.UserName
import com.shhatrat.loggerek.api.oauth.OAuthLogic
import com.shhatrat.loggerek.api.oauth.OAuthLogic.buildOAuthHeader
import com.shhatrat.loggerek.api.oauth.OAuthLogic.generateSignature
import com.shhatrat.loggerek.api.oauth.OAuthParams
import com.shhatrat.loggerek.api.oauth.OAuthParams.getRequestTokenParams
import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText

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

    override suspend fun accessToken(
        pin: String,
        token: String,
        tokenSecret: String
    ): OAuthAccessTokenResponse {
        val oauthParams = OAuthParams.getAccessTokenParams(pin, token)
        val responseBody = sendOAuthRequest(
            url = OpencachingApi.Url.accessToken(),
            oauthParams = oauthParams,
            tokenSecret = tokenSecret
        )
        return OAuthLogic.parseAccessTokenResponse(responseBody)
    }

    override suspend fun cache(cacheId: String): String {
        val g = client.get(OpencachingApi.Url.geocache()) {
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

    override suspend fun getFullCache(
        cacheId: String,
        token: String,
        tokenSecret: String
    ): Geocache {
        return getCache(client, token, tokenSecret, cacheId)
    }

    override suspend fun saveNote(
        cacheId: String,
        token: String,
        tokenSecret: String,
        noteToSave: String,
        oldValue: String
    ) {
        saveNoteToApi(client, token, tokenSecret, cacheId, noteToSave, oldValue)
    }

    override suspend fun logCapabilities(
        cacheId: String,
        token: String,
        tokenSecret: String
    ): LogTypeResponse {
        return logCapabilities(client, token, tokenSecret, cacheId)
    }

    override suspend fun submitLog(
        submitLogData: SubmitLogData,
        token: String,
        tokenSecret: String
    ): LogResponse {
        return submitLog(client, token, tokenSecret, submitLogData)
    }

    override suspend fun searchByName(
        name: String,
        token: String,
        tokenSecret: String
    ): SearchResponse {
        return searchByName(client, token, tokenSecret, name)
    }

    override suspend fun geocaches(
        geocacheCodes: List<String>,
        token: String,
        tokenSecret: String
    ): List<Geocache> {
        return getGeocaches(client, token, tokenSecret, geocacheCodes)
    }
}