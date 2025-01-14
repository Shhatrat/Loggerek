package com.shhatrat.loggerek.api

import com.shhatrat.loggerek.api.oauth.model.OAuthAccessTokenResponse
import com.shhatrat.loggerek.api.oauth.model.OAuthRequestTokenResponse

class FakeApi: Api {

    override suspend fun requestToken(): OAuthRequestTokenResponse {
        return OAuthRequestTokenResponse(token = "fakeToken", tokenSecret = "fakeTokenSecret", url = "https://FakeUrl.com")
    }

    override suspend fun accessToken(
        pin: String,
        token: String,
        tokenSecret: String
    ): OAuthAccessTokenResponse {
        return OAuthAccessTokenResponse(oauthToken = "oauthToken", oauthTokenSecret = "oauthTokenSecret")
    }

    override suspend fun cache(cacheId: String): String {
        return "{\"code\":\"OP1111\",\"name\":\"FakeName\",\"location\":\"52.111111|16.111111\",\"status\":\"Available\",\"type\":\"Traditional\"}\n"
    }

    override suspend fun getLoggedUserNickname(token: String, tokenSecret: String): String {
        return "FakeUser"
    }
}