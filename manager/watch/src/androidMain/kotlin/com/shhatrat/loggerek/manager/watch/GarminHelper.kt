package com.shhatrat.loggerek.manager.watch

import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.garmin.android.connectiq.IQDevice.IQDeviceStatus.*

fun IQDevice.IQDeviceStatus.toGarminWatchState(): GarminWatch.GarminWatchState {
    return when(this){
        NOT_PAIRED -> GarminWatch.GarminWatchState.NOT_PAIRED
        NOT_CONNECTED -> GarminWatch.GarminWatchState.NOT_CONNECTED
        CONNECTED -> GarminWatch.GarminWatchState.CONNECTED
        UNKNOWN -> GarminWatch.GarminWatchState.UNKNOWN
    }
}

fun IQApp.IQAppStatus.toInstalledAppState(): GarminWatch.InstalledAppState{
    return when(this){
        IQApp.IQAppStatus.UNKNOWN -> GarminWatch.InstalledAppState.UNKNOWN
        IQApp.IQAppStatus.INSTALLED -> GarminWatch.InstalledAppState.INSTALLED
        IQApp.IQAppStatus.NOT_INSTALLED -> GarminWatch.InstalledAppState.NOT_INSTALLED
        IQApp.IQAppStatus.NOT_SUPPORTED -> GarminWatch.InstalledAppState.NOT_SUPPORTED
    }
}