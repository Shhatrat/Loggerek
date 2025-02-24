package com.shhatrat.loggerek.api

/**
 * Contains constants and utility methods for interacting with the OpenCaching API.
 * This object provides base URLs, consumer credentials, and specific endpoint paths.
 */
internal object OpencachingApi {

    /**
     * The base URL for all OpenCaching API requests.
     */
    internal const val baseUrl = "https://opencaching.pl/okapi/"

    /**
     * The consumer key used for OAuth authentication with the OpenCaching API.
     */
    internal const val consumerKey = "dEmyvudTJYeYMkZTFwug"

    /**
     * The consumer secret used for OAuth authentication with the OpenCaching API.
     */
    internal const val consumerSecret = "T5aX8PAvfQcGbwWBG5ZnMGyf7R7vJPFAfy6pY22g"

    // Private constants defining endpoint parts
    private const val geocachePart = "services/caches/geocache"
    private const val userPart = "services/users/user"
    private const val accessTokenPart = "services/oauth/access_token"
    private const val requestTokenPart = "services/oauth/request_token"
    private const val saveNotesPart = "services/caches/save_personal_notes"
    private const val logCapabilities = "services/logs/capabilities"
    private const val submitLog = "services/logs/submit"
    private const val searchAll = "services/caches/search/all"
    private const val geocaches = "services/caches/geocaches"
    private const val nearest = "services/caches/search/nearest"


    /**
     * Provides methods for building URLs to specific API endpoints.
     */
    internal object Url {
        /**
         * Returns the full URL for the geocache endpoint.
         *
         * @return A [String] representing the geocache endpoint URL.
         */
        fun geocache() = "$baseUrl$geocachePart"

        /**
         * Returns the full URL for the access token endpoint.
         *
         * @return A [String] representing the access token endpoint URL.
         */
        fun accessToken() = "$baseUrl$accessTokenPart"

        /**
         * Returns the full URL for the request token endpoint.
         *
         * @return A [String] representing the request token endpoint URL.
         */
        fun requestToken() = "$baseUrl$requestTokenPart"

        /**
         * Returns the full URL for the user endpoint.
         *
         * @return A [String] representing the user endpoint URL.
         */
        fun user() = "$baseUrl$userPart"

        fun saveNotes() = "$baseUrl$saveNotesPart"

        fun logCapabilities() = "$baseUrl$logCapabilities"

        fun submitLog() = "$baseUrl$submitLog"

        fun searchAll() = "$baseUrl${searchAll}"

        fun geocaches() = "$baseUrl${geocaches}"

        fun nearest() = "$baseUrl$nearest"
    }
}