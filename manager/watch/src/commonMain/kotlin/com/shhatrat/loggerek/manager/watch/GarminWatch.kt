package com.shhatrat.loggerek.manager.watch

interface GarminWatch: Watch {

    data class GarminDevice(val name: String, val identifier: Long, val connectedState: String)

    fun getListOfDevices(): List<GarminDevice>

    suspend fun isAppInstalled(garminDevice: GarminDevice) : Boolean

    fun selectDevice(garminDevice: GarminDevice)

    suspend fun loadApp(): Boolean
}