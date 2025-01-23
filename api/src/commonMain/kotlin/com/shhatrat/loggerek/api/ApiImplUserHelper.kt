package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.OpencachingParam
import com.shhatrat.loggerek.api.model.OpencachingParam.Companion.parseForApi
import com.shhatrat.loggerek.api.oauth.OAuthLogic.buildOAuthHeader
import com.shhatrat.loggerek.api.oauth.OAuthLogic.generateSignature
import com.shhatrat.loggerek.api.oauth.OAuthParams.generateOAuthNonce
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import kotlinx.datetime.Clock

internal suspend inline fun <reified T>ApiImpl.getUser(client: HttpClient, token: String, tokenSecret: String, params: Collection<OpencachingParam.User>): T {
    val p = mapOf(
        "oauth_consumer_key" to OpencachingApi.consumerKey,
        "oauth_nonce" to generateOAuthNonce(),
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to (Clock.System.now().toEpochMilliseconds()).toString(),
        "oauth_version" to "1.0",
        "oauth_token" to token,
        "fields" to parseForApi(params)
    )
    val signature = generateSignature("GET", OpencachingApi.Url.user(), p, OpencachingApi.consumerSecret, tokenSecret)
    val signedParams = p + ("oauth_signature" to signature)
    val response: HttpResponse = client.get("${OpencachingApi.Url.user()}?fields=${parseForApi(params)}") {
        headers {
            append("Authorization", buildOAuthHeader(signedParams.filter { it.key.startsWith("oauth") }))
        }
    }
    return response.body<T>()

}

internal suspend inline fun <reified T>ApiImpl.getCache(client: HttpClient, token: String, tokenSecret: String, params: Collection<OpencachingParam.Geocache>): T {
    val p = mapOf(
        "oauth_consumer_key" to OpencachingApi.consumerKey,
        "oauth_nonce" to generateOAuthNonce(),
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to (Clock.System.now().toEpochMilliseconds()).toString(),
        "oauth_version" to "1.0",
        "oauth_token" to token,
        "cache_code" to "OP0001",
        "fields" to parseForApi(params)
    )

    val signature = generateSignature("GET", OpencachingApi.Url.geocache(), p, OpencachingApi.consumerSecret, tokenSecret)
    val signedParams = p + ("oauth_signature" to signature)
    val response: HttpResponse = client.get("${OpencachingApi.Url.geocache()}?cache_code=OP0001&fields=${parseForApi(params)}") {
        headers {
            append("Authorization", buildOAuthHeader(signedParams.filter { it.key.startsWith("oauth") }))
        }
    }
    return response.body<T>()
}