package com.shhatrat.loggerek.manager.watch.logic

interface WatchLogic {

    fun start()

    fun stop()

    suspend fun isAvailable(): Boolean
}