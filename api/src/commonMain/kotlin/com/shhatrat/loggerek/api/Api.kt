package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.UserName
import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse

/**
 * Interface defining API operations for interacting with the OpenCaching system.
 */
interface Api {

    /**
     * Requests an OAuth request token from the OpenCaching API.
     *
     * @return [OAuthRequestTokenResponse] containing the request token and related details.
     * @throws Exception if the request fails or the response cannot be parsed.
     */
    suspend fun requestToken(): OAuthRequestTokenResponse

    /**
     * Exchanges the provided pin and request token for an OAuth access token.
     *
     * @param pin The pin code provided by the user after authentication.
     * @param token The request token obtained during the initial authentication phase.
     * @param tokenSecret The secret associated with the request token.
     * @return [OAuthAccessTokenResponse] containing the access token and secret.
     * @throws Exception if the request fails or the response cannot be parsed.
     */
    suspend fun accessToken(pin: String, token: String, tokenSecret: String): OAuthAccessTokenResponse

    /**
     * Fetches information about a specific geocache using its cache ID.
     *
     * @param cacheId The unique identifier of the geocache.
     * @return A [String] containing the raw geocache data.
     * @throws Exception if the request fails or the response cannot be parsed.
     */
    suspend fun cache(cacheId: String): String

    suspend fun getLoggedUserNickname(token: String, tokenSecret: String): UserName

    suspend fun getLoggedUserData(token: String, tokenSecret: String): FullUser

    suspend fun getFullCache(cacheId: String, token: String, tokenSecret: String): Geocache
}