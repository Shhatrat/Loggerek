package com.shhatrat.loggerek.manager.watch

import com.shhatrat.loggerek.manager.watch.config.ConfigManager
import com.shhatrat.loggerek.repository.Repository

class GarminConfigManager(
    private val repository: Repository,
    private val garminWatch: GarminWatch
): ConfigManager<GarminWatch.GarminDevice, GarminWatch.InstalledAppState> {

    override suspend fun init(): Boolean {
        return garminWatch.init()
    }

    override suspend fun getSavedItem(): ConfigManager.DeviceItem<GarminWatch.GarminDevice, GarminWatch.InstalledAppState>? {
        if(repository.garminIdentifier.get() == null){
            return null
        }
        return getDevices().firstOrNull { it.device.identifier == repository.garminIdentifier.get() }
    }

    override suspend fun getDevices(): List<ConfigManager.DeviceItem<GarminWatch.GarminDevice, GarminWatch.InstalledAppState>> {
        return garminWatch.getListOfDevices().map {
            ConfigManager.DeviceItem(
                device = it,
                isAppInstalled =  garminWatch.isAppInstalled(it)
            )
        }
    }

    override fun saveDevice(device: GarminWatch.GarminDevice?) {
        device?.let { garminWatch.selectDevice(it) }
        repository.garminIdentifier.save(device?.identifier)
    }
}