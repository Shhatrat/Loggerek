package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.Geocache
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
import io.ktor.http.encodeURLParameter
import kotlinx.datetime.Clock

internal suspend inline fun <reified T> ApiImpl.getUser(
    client: HttpClient,
    token: String,
    tokenSecret: String,
    params: Collection<OpencachingParam.User>
): T {
    return makeLevel3Request(client, token, tokenSecret, OpencachingApi.Url.user(), mapOf(), params)
}


private suspend inline fun <reified T> makeLevel3Request(
    client: HttpClient,
    token: String,
    tokenSecret: String,
    url: String,
    params: Map<String, String>,
    fieldsParams: Collection<OpencachingParam>
): T {
    val p = mapOf(
        "oauth_consumer_key" to OpencachingApi.consumerKey,
        "oauth_nonce" to generateOAuthNonce(),
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to (Clock.System.now().toEpochMilliseconds()).toString(),
        "oauth_version" to "1.0",
        "oauth_token" to token,
        "fields" to parseForApi(fieldsParams)
    ).plus(params)

    val signature = generateSignature("GET", url, p, OpencachingApi.consumerSecret, tokenSecret)
    val signedParams = p + ("oauth_signature" to signature)
    val response: HttpResponse =
        client.get("${url}?${params.parseParams()}fields=${parseForApi(fieldsParams)}") {
            headers {
                append(
                    "Authorization",
                    buildOAuthHeader(signedParams.filter { it.key.startsWith("oauth") })
                )
            }
        }
    return response.body<T>()
}

private suspend inline fun <reified T> makeLevel3Request(
    client: HttpClient,
    token: String,
    tokenSecret: String,
    url: String,
    params: Map<String, String>,
): T {
    val p = mapOf(
        "oauth_consumer_key" to OpencachingApi.consumerKey,
        "oauth_nonce" to generateOAuthNonce(),
        "oauth_signature_method" to "HMAC-SHA1",
        "oauth_timestamp" to (Clock.System.now().toEpochMilliseconds()).toString(),
        "oauth_version" to "1.0",
        "oauth_token" to token
    ).plus(params)

    val signature = generateSignature("GET", url, p, OpencachingApi.consumerSecret, tokenSecret)
    val signedParams = p + ("oauth_signature" to signature)
    val response: HttpResponse =
        client.get("${url}?${params.parseParamsWithoutEnd()}") {
            headers {
                append(
                    "Authorization",
                    buildOAuthHeader(signedParams.filter { it.key.startsWith("oauth") })
                )
            }
        }
    return response.body<T>()
}

private fun Map<String, String>.parseParams(): String {
    return if (this.isEmpty()) ""
    else
        this.map { "${it.key}=${it.value}" }
            .joinToString(separator = "&", prefix = "", postfix = "&")
}

private fun Map<String, String>.parseParamsWithoutEnd(): String {
    return if (this.isEmpty()) ""
    else
        this.map { "${it.key}=${it.value}" }
            .joinToString(separator = "&", prefix = "", postfix = "")
}

internal suspend inline fun ApiImpl.getCache(
    client: HttpClient,
    token: String,
    tokenSecret: String,
    geocacheId: String,
): Geocache {
    return makeLevel3Request<Geocache>(
        client,
        token,
        tokenSecret,
        OpencachingApi.Url.geocache(),
        mapOf(Pair("cache_code", geocacheId)),
        OpencachingParam.Geocache.getAll()
    )
}


internal suspend inline fun ApiImpl.saveNoteToApi(
    client: HttpClient,
    token: String,
    tokenSecret: String,
    geocacheId: String,
    noteToSave: String,
    oldValue: String
) {
    makeLevel3Request<Unit>(
        client,
        token,
        tokenSecret,
        OpencachingApi.Url.saveNotes(),k
        mapOf(
            Pair("cache_code", geocacheId),
            Pair("new_value", noteToSave.encodeURLParameter()),
            Pair("old_value", oldValue.encodeURLParameter())
        ),
    )
}

