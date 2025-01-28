package com.shhatrat.loggerek.api.oauth

import com.shhatrat.loggerek.api.OpencachingApi
import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse
import io.ktor.http.encodeURLParameter
import okio.ByteString.Companion.encodeUtf8

object OAuthLogic {

    private fun parseOAuthResponse(response: String): Map<String, String> {
        return response.split("&").associate {
            val (key, value) = it.split("=")
            key to value
        }
    }

    internal fun parseRequestTokenResponse(response: String): OAuthRequestTokenResponse {
        val parsedResponse = parseOAuthResponse(response)
        val token = parsedResponse["oauth_token"]!!
        val tokenSecret = parsedResponse["oauth_token_secret"]!!
        val url = "${OpencachingApi.baseUrl}apps/authorize?oauth_token=$token"
        return OAuthRequestTokenResponse(
            token = token,
            tokenSecret = tokenSecret,
            url = url
        )
    }

    internal fun buildOAuthHeader(params: Map<String, String>): String {
        return "OAuth " + params.entries.joinToString(", ") { "${it.key}=\"${it.value.encodeURLParameter()}\"" }
    }

    internal fun generateSignature(
        method: String,
        url: String,
        params: Map<String, String>,
        consumerSecret: String,
        tokenSecret: String? = ""
    ): String {
        val sortedParams = params.entries.sortedBy { it.key }
        val paramString = sortedParams.joinToString("&") { "${it.key}=${it.value}" }
        val baseString = "$method&${url.encodeURLParameter()}&${paramString.encodeURLParameter()}"
        val signingKey = "$consumerSecret&$tokenSecret"

        val keyBytes = signingKey.encodeUtf8()
        val dataBytes = baseString.encodeUtf8()
        val hmac = dataBytes.hmacSha1(keyBytes)
        return hmac.base64()
    }

    fun parseAccessTokenResponse(body: String): OAuthAccessTokenResponse {
        return OAuthAccessTokenResponse(
            oauthToken = parseOAuthResponse(body)["oauth_token"]!!,
            oauthTokenSecret = parseOAuthResponse(body)["oauth_token_secret"]!!
        )
    }
}