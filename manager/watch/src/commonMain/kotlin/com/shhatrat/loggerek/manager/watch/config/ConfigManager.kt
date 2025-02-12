package com.shhatrat.loggerek.manager.watch.config

interface ConfigManager<DeviceType, AppInstalledState> {

    data class DeviceItem<DeviceType, AppInstalledState>(
        val device: DeviceType,
        val isAppInstalled: AppInstalledState
    )

    suspend fun init(): Boolean

    suspend fun getSavedItem(): DeviceItem<DeviceType, AppInstalledState>?

    suspend fun getDevices(): List<DeviceItem<DeviceType, AppInstalledState>>

    fun saveDevice(device: DeviceType?)
}