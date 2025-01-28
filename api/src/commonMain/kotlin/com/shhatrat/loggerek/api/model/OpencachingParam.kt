package com.shhatrat.loggerek.api.model

sealed class OpencachingParam(val apiKey: String) {

    companion object {
        fun parseForApi(params: Collection<OpencachingParam>): String {
            return params.joinToString(separator = "%7C", prefix = "", postfix = "") { it.apiKey }
        }

        fun parseForApiNotFormatted(params: Collection<OpencachingParam>): String {
            return params.joinToString(separator = "|", prefix = "", postfix = "") { it.apiKey }
        }
    }

    sealed class User(apiKey: String) : OpencachingParam(apiKey) {
        companion object {
            const val API_USERNAME = "username"
            const val API_CACHES_FOUND = "caches_found"
            const val API_CACHES_NOT_FOUND = "caches_notfound"
            const val API_CACHES_HIDDEN = "caches_hidden"
            const val API_RECOMMENDATIONS_GIVEN = "rcmds_given"
            const val API_RECOMMENDATIONS_LEFT = "rcmds_left"
            fun getAll() = listOf(
                USERNAME,
                CACHES_FOUND,
                CACHES_NOT_FOUND,
                CACHES_HIDDEN,
                RECOMMENDATIONS_GIVEN,
                RECOMMENDATIONS_LEFT
            )
        }

        data object USERNAME : User(API_USERNAME)
        data object CACHES_FOUND : User(API_CACHES_FOUND)
        data object CACHES_NOT_FOUND : User(API_CACHES_NOT_FOUND)
        data object CACHES_HIDDEN : User(API_CACHES_HIDDEN)
        data object RECOMMENDATIONS_GIVEN : User(API_RECOMMENDATIONS_GIVEN)
        data object RECOMMENDATIONS_LEFT : User(API_RECOMMENDATIONS_LEFT)
    }

    sealed class GeocacheUser(apiKey: String): OpencachingParam(apiKey){
        companion object {
            const val GEOCACHE_API_USER_UUID = "uuid"
            const val GEOCACHE_API_USER_USERNAME = "username"
            const val GEOCACHE_API_USER_USER_PROFILE = "profile_url"
        }
    }

    sealed class GeocacheStatus(apiKey: String): OpencachingParam(apiKey){
        companion object {
            const val GEOCACHE_API_STATUS_AVAILABLE = "Available"
            const val GEOCACHE_API_STATUS_TEMPORARILY_UNAVAILABLE = "Temporarily unavailable"
            const val GEOCACHE_API_STATUS_ARCHIVED = "Archived"
        }
    }

    sealed class Geocache(apiKey: String) : OpencachingParam(apiKey) {
        companion object {
            const val GEOCACHE_API_CODE = "code"
            const val GEOCACHE_API_NAME = "name"
            const val GEOCACHE_API_LOCATION = "location"
            const val GEOCACHE_API_TYPE = "type"
            const val GEOCACHE_API_STATUS = "status"
            const val GEOCACHE_API_DIFFICULTY = "difficulty"
            const val GEOCACHE_API_TERRAIN = "terrain"
            const val GEOCACHE_API_SIZE = "size2"
            const val GEOCACHE_API_OWNER = "owner"
            const val GEOCACHE_API_DATE_HIDDEN = "date_hidden"
            const val GEOCACHE_API_FOUNDS = "founds"
            const val GEOCACHE_API_NOT_FOUNDS = "notfounds"
            const val GEOCACHE_API_RATINGS = "rating"
            const val GEOCACHE_API_DESCRIPTION = "description"
            const val GEOCACHE_API_HINT = "hint"
            const val GEOCACHE_API_URL = "url"
            const val GEOCACHE_API_RECOMMENDATIONS = "recommendations"
            const val GEOCACHE_API_MY_NOTES = "my_notes"
            const val GEOCACHE_API_REQ_PASSWORD = "req_passwd"

            fun getAll() = listOf(
                CODE,
                NAME,
                LOCATION,
                TYPE,
                STATUS,
                URL,
                DIFFICULTY,
                TERRAIN,
                SIZE,
                OWNER,
                DATE_HIDDEN,
                FOUNDS,
                NOT_FOUNDS,
                RATINGS,
                DESCRIPTION,
                HINT,
                RECOMMENDATIONS,
                MY_NOTES,
                REQ_PASSWORD
            )
        }

        data object CODE : Geocache(GEOCACHE_API_CODE)
        data object NAME : Geocache(GEOCACHE_API_NAME)
        data object LOCATION : Geocache(GEOCACHE_API_LOCATION)
        data object TYPE : Geocache(GEOCACHE_API_TYPE)
        data object STATUS : Geocache(GEOCACHE_API_STATUS)
        data object URL : Geocache(GEOCACHE_API_URL)
        data object DIFFICULTY : Geocache(GEOCACHE_API_DIFFICULTY)
        data object TERRAIN : Geocache(GEOCACHE_API_TERRAIN)
        data object SIZE : Geocache(GEOCACHE_API_SIZE)
        data object OWNER : Geocache(GEOCACHE_API_OWNER)
        data object DATE_HIDDEN : Geocache(GEOCACHE_API_DATE_HIDDEN)
        data object FOUNDS : Geocache(GEOCACHE_API_FOUNDS)
        data object NOT_FOUNDS : Geocache(GEOCACHE_API_NOT_FOUNDS)
        data object RATINGS : Geocache(GEOCACHE_API_RATINGS)
        data object DESCRIPTION : Geocache(GEOCACHE_API_DESCRIPTION)
        data object HINT : Geocache(GEOCACHE_API_HINT)
        data object RECOMMENDATIONS : Geocache(GEOCACHE_API_RECOMMENDATIONS)
        data object MY_NOTES : Geocache(GEOCACHE_API_MY_NOTES)
        data object REQ_PASSWORD : Geocache(GEOCACHE_API_REQ_PASSWORD)
    }
}