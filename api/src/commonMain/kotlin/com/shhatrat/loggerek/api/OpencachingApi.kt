package com.shhatrat.loggerek.api

/**
 * Contains constants and utility methods for interacting with the OpenCaching API.
 * This object provides base URLs, consumer credentials, and specific endpoint paths.
 */
internal object OpencachingApi {
    /**
     * The base URL for all OpenCaching API requests.
     */
    internal const val BASE_URL = "https://opencaching.pl/okapi/"

    /**
     * The consumer key used for OAuth authentication with the OpenCaching API.
     */
    internal const val CONSUMER_KEY = "dEmyvudTJYeYMkZTFwug"

    /**
     * The consumer secret used for OAuth authentication with the OpenCaching API.
     */
    internal const val CONSUMER_SECRET = "T5aX8PAvfQcGbwWBG5ZnMGyf7R7vJPFAfy6pY22g"

    // Private constants defining endpoint parts
    private const val GEOCACHE_PART = "services/caches/geocache"
    private const val USER_PART = "services/users/user"
    private const val ACCESS_TOKEN_PART = "services/oauth/access_token"
    private const val REQUEST_TOKEN_PART = "services/oauth/request_token"
    private const val SAVE_NOTES_PART = "services/caches/save_personal_notes"
    private const val LOG_CAPABILITIES = "services/logs/capabilities"
    private const val SUBMIT_LOG = "services/logs/submit"
    private const val SEARCH_ALL = "services/caches/search/all"
    private const val GEOCACHES = "services/caches/geocaches"
    private const val NEAREST = "services/caches/search/nearest"

    /**
     * Provides methods for building URLs to specific API endpoints.
     */
    internal object Url {
        /**
         * Returns the full URL for the geocache endpoint.
         *
         * @return A [String] representing the geocache endpoint URL.
         */
        fun geocache() = "$BASE_URL$GEOCACHE_PART"

        /**
         * Returns the full URL for the access token endpoint.
         *
         * @return A [String] representing the access token endpoint URL.
         */
        fun accessToken() = "$BASE_URL$ACCESS_TOKEN_PART"

        /**
         * Returns the full URL for the request token endpoint.
         *
         * @return A [String] representing the request token endpoint URL.
         */
        fun requestToken() = "$BASE_URL$REQUEST_TOKEN_PART"

        /**
         * Returns the full URL for the user endpoint.
         *
         * @return A [String] representing the user endpoint URL.
         */
        fun user() = "$BASE_URL$USER_PART"

        fun saveNotes() = "$BASE_URL$SAVE_NOTES_PART"

        fun logCapabilities() = "$BASE_URL$LOG_CAPABILITIES"

        fun submitLog() = "$BASE_URL$SUBMIT_LOG"

        fun searchAll() = "$BASE_URL$SEARCH_ALL"

        fun geocaches() = "$BASE_URL$GEOCACHES"

        fun nearest() = "$BASE_URL$NEAREST"
    }
}
