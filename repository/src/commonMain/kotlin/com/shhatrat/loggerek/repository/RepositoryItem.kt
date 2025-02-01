package com.shhatrat.loggerek.repository

interface RepositoryItem<T> {
    fun save(obj: T)
    fun get(): T
    fun remove()
}