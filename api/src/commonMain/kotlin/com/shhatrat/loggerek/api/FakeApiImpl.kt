package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.model.FullUser
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogResponse
import com.shhatrat.loggerek.api.model.LogTypeResponse
import com.shhatrat.loggerek.api.model.SearchResponse
import com.shhatrat.loggerek.api.model.SubmitLogData
import com.shhatrat.loggerek.api.model.UserName
import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse

@Suppress("ktlint:standard:max-line-length")
class FakeApiImpl : Api {
    override suspend fun requestToken(): OAuthRequestTokenResponse =
        OAuthRequestTokenResponse(
            token = "fakeToken",
            tokenSecret = "fakeTokenSecret",
            url = "https://FakeUrl.com",
        )

    override suspend fun accessToken(
        pin: String,
        token: String,
        tokenSecret: String,
    ): OAuthAccessTokenResponse =
        OAuthAccessTokenResponse(
            oauthToken = "oauthToken",
            oauthTokenSecret = "oauthTokenSecret",
        )

    override suspend fun cache(cacheId: String): String =
        "{\"code\":\"OP1111\",\"name\":\"FakeName\",\"location\":\"52.111111|16.111111\",\"status\":\"Available\",\"type\":\"Traditional\"}\n"

    override suspend fun getLoggedUserNickname(
        token: String,
        tokenSecret: String,
    ): UserName = UserName("FakeUserName")

    override suspend fun getLoggedUserData(
        token: String,
        tokenSecret: String,
    ): FullUser = FullUser.mock()

    override suspend fun getFullCache(
        cacheId: String,
        token: String,
        tokenSecret: String,
    ): Geocache {
        TODO("Not yet implemented")
    }

    override suspend fun saveNote(
        cacheId: String,
        token: String,
        tokenSecret: String,
        noteToSave: String,
        oldValue: String,
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun logCapabilities(
        cacheId: String,
        token: String,
        tokenSecret: String,
    ): LogTypeResponse {
        TODO("Not yet implemented")
    }

    override suspend fun submitLog(
        submitLogData: SubmitLogData,
        token: String,
        tokenSecret: String,
    ): LogResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchByName(
        name: String,
        token: String,
        tokenSecret: String,
    ): SearchResponse {
        TODO("Not yet implemented")
    }

    override suspend fun geocaches(
        geocacheCodes: List<String>,
        token: String,
        tokenSecret: String,
    ): List<Geocache> {
        TODO("Not yet implemented")
    }

    override suspend fun nearestGeocaches(
        center: String,
        limit: Int,
        token: String,
        tokenSecret: String,
    ): List<Geocache> {
        TODO("Not yet implemented")
    }
}
