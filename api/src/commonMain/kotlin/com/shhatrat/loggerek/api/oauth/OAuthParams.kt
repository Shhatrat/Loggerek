package com.shhatrat.loggerek.api.oauth

import com.shhatrat.loggerek.api.OpencachingApi
import kotlinx.datetime.Clock
import kotlin.random.Random

object OAuthParams {

    private fun createBaseParams(consumerKey: String): Map<String, String> {
        return mapOf(
            "oauth_consumer_key" to consumerKey,
            "oauth_nonce" to generateOAuthNonce(),
            "oauth_signature_method" to "HMAC-SHA1",
            "oauth_timestamp" to (Clock.System.now().toEpochMilliseconds()).toString(),
            "oauth_version" to "1.0"
        )
    }

    internal fun getRequestTokenParams() = createBaseParams(OpencachingApi.consumerKey).plus("oauth_callback" to "oob")

    internal fun getAccessTokenParams(pin: String, token: String): Map<String, String> {
        return createBaseParams(OpencachingApi.consumerKey)
            .plus("oauth_verifier" to pin)
            .plus("oauth_token" to token)
    }

    internal fun generateOAuthNonce(): String {
        val timestamp = Clock.System.now().toEpochMilliseconds()
        val randomPart = Random.nextInt(100000, 999999)
        return "$timestamp$randomPart"
    }
}