package com.shhatrat.loggerek.manager.watch

interface GarminWatch : Watch {

    enum class InstalledAppState {
        UNKNOWN,
        INSTALLED,
        NOT_INSTALLED,
        NOT_SUPPORTED;
    }

    enum class GarminWatchState {
        NOT_PAIRED,
        NOT_CONNECTED,
        CONNECTED,
        UNKNOWN;
    }

    data class GarminDevice(val name: String, val identifier: Long, val connectedState: GarminWatchState)

    fun getListOfDevices(): List<GarminDevice>

    suspend fun isAppInstalled(garminDevice: GarminDevice): InstalledAppState

    fun selectDevice(garminDevice: GarminDevice)

    suspend fun loadApp(): Boolean
}