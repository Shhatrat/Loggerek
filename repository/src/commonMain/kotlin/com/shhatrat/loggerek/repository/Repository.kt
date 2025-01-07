package com.shhatrat.loggerek.repository


interface Repository {
    val token: RepositoryItem<String>
    val tokenSecret: RepositoryItem<String>
}
