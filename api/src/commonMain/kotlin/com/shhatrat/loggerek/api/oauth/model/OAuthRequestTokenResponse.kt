package com.shhatrat.loggerek.api.oauth.model

/**
 * Represents the response received after requesting an OAuth request token.
 *
 * @property token The OAuth request token provided by the API.
 * @property tokenSecret The secret associated with the request token.
 * @property url The URL where the user can authenticate using the request token.
 */
data class OAuthRequestTokenResponse(
    val token: String,
    val tokenSecret: String,
    val url: String,
)
