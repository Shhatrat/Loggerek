package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.UserParam
import com.shhatrat.loggerek.api.oauth.OAuthLogic.buildOAuthHeader
import com.shhatrat.loggerek.api.oauth.OAuthLogic.generateSignature
import com.shhatrat.loggerek.api.oauth.OAuthParams.generateOAuthNonce
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import kotlinx.datetime.Clock

internal suspend inline fun <reified T>ApiImpl.getUser(client: HttpClient, token: String, tokenSecret: String, params: Collection<UserParam>): T {
    val p = mapOf(
        "oauth_consumer_key" to OpencachingApi.consumerKey,
        "oauth_nonce" to generateOAuthNonce(),
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to (Clock.System.now().toEpochMilliseconds()).toString(),
        "oauth_version" to "1.0",
        "oauth_token" to token,
        "fields" to getUserNameFields(params)
    )
    val signature = generateSignature("GET", OpencachingApi.Url.user(), p, OpencachingApi.consumerSecret, tokenSecret)
    val signedParams = p + ("oauth_signature" to signature)
    val response: HttpResponse = client.get("${OpencachingApi.Url.user()}?fields=${getUserNameFields(params)}") {
        headers {
            append("Authorization", buildOAuthHeader(signedParams.filter { it.key.startsWith("oauth") }))
        }
    }
    return response.body<T>()

}

private fun getUserNameFields(params: Collection<UserParam>): String{
    return params.joinToString(separator = "%7C", prefix = "", postfix = "") { it.apiName }
}