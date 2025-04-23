package com.shhatrat.loggerek.api.oauth.model

/**
 * Represents the response received after exchanging a request token for an access token.
 *
 * @property oauthToken The OAuth access token provided by the API.
 * @property oauthTokenSecret The secret associated with the access token.
 */
data class OAuthAccessTokenResponse(
    val oauthToken: String,
    val oauthTokenSecret: String,
)
