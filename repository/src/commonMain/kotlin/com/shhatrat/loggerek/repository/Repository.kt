package com.shhatrat.loggerek.repository

import com.shhatrat.loggerek.base.exception.NoTokenException


interface Repository {
    val token: RepositoryItem<String?>
    val tokenSecret: RepositoryItem<String?>
    val savePassword: RepositoryItem<Boolean>
    val tryMixedPassword: RepositoryItem<Boolean>
    val garminIdentifier: RepositoryItem<Long?>
    val logs: RepositoryItem<List<QuickLog>>

    fun safeTokenAndTokenSecret(): TokenData {
        try {
            return TokenData(token.get()!!, tokenSecret.get()!!)
        } catch (e: Exception) {
            throw NoTokenException()
        }
    }

    data class TokenData(val token: String, val tokenSecret: String)
}
