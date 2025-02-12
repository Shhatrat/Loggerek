package com.shhatrat.loggerek.manager.watch

import android.content.Context
import android.util.Log
import com.garmin.android.connectiq.ConnectIQ
import com.garmin.android.connectiq.ConnectIQ.ConnectIQListener
import com.garmin.android.connectiq.ConnectIQ.IQApplicationEventListener
import com.garmin.android.connectiq.ConnectIQ.IQApplicationInfoListener
import com.garmin.android.connectiq.ConnectIQ.IQMessageStatus.SUCCESS
import com.garmin.android.connectiq.ConnectIQ.IQSendMessageListener
import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine

actual class GarminManager(
    private val context: Context,
    private val garminInitType: GarminInitType
) : GarminWatch {

    private lateinit var connectIQ: ConnectIQ
    private val deviceList = mutableListOf<Pair<IQDevice, GarminWatch.GarminDevice>>()
    private var selectedDevice: Pair<IQDevice, GarminWatch.GarminDevice>? = null

    private var app: IQApp? = when (garminInitType) {
        GarminInitType.REAL -> null
        GarminInitType.FAKE -> IQApp("")
    }

    private var alreadyInited = false

    private val device: IQDevice
        get() {
            return selectedDevice?.first ?: deviceList.first().first
        }

    override fun selectDevice(garminDevice: GarminWatch.GarminDevice) {
        selectedDevice = deviceList.find { it.second.identifier == garminDevice.identifier }
    }

    override suspend fun loadApp(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            connectIQ.getApplicationInfo(
                garminInitType.appId,
                selectedDevice?.first,
                object : IQApplicationInfoListener {
                    override fun onApplicationInfoReceived(p0: IQApp?) {
                        if (p0 != null) {
                            app = p0
                            continuation.resumeWith(Result.success(true))
                        } else
                            continuation.resumeWith(Result.success(false))
                    }

                    override fun onApplicationNotInstalled(p0: String?) {
                        continuation.resumeWith(Result.success(false))
                    }
                })
        }
    }

    private fun saveDevices(list: List<IQDevice>) {
        deviceList.clear()
        deviceList.addAll(list.map {
            Pair(
                it,
                GarminWatch.GarminDevice(
                    name = it.friendlyName,
                    identifier = it.deviceIdentifier,
                    connectedState = it.status.toGarminWatchState()
                )
            )
        })
    }

    override suspend fun init(): Boolean {
        if (alreadyInited) {
            saveDevices(connectIQ.knownDevices)
            return true
        }

        return suspendCancellableCoroutine { continuation ->
            connectIQ = ConnectIQ.getInstance(context, garminInitType.type)
            connectIQ.initialize(context, true, object : ConnectIQListener {
                override fun onSdkReady() {
                    Log.d("TAGgarmin", "onSdkReady: Success")
                    alreadyInited = true
                    saveDevices(connectIQ.knownDevices)
                    continuation.resumeWith(Result.success(true))
                }

                override fun onInitializeError(error: ConnectIQ.IQSdkErrorStatus?) {
                    Log.d("TAGgarmin", "onInitializeError: $error")
                    continuation.resumeWith(Result.success(false))
                }

                override fun onSdkShutDown() {
                    Log.d("TAGgarmin", "onSdkShutDown:")
                }
            })
        }
    }

    override fun getListOfDevices(): List<GarminWatch.GarminDevice> {
        return connectIQ.knownDevices
            .apply { saveDevices(this) }
            .map {
                GarminWatch.GarminDevice(
                    name = it.friendlyName,
                    identifier = it.deviceIdentifier,
                    connectedState = connectIQ.getDeviceStatus(it).toGarminWatchState()
                )
            }
    }

    override suspend fun isAppInstalled(garminDevice: GarminWatch.GarminDevice): GarminWatch.InstalledAppState {
        return suspendCancellableCoroutine { continuation ->
            val dev = deviceList.find { it.second.identifier == garminDevice.identifier }?.first
            if(dev?.status != IQDevice.IQDeviceStatus.CONNECTED){
                continuation.resumeWith(Result.success(GarminWatch.InstalledAppState.UNKNOWN))
            }else {
                connectIQ.getApplicationInfo(
                    garminInitType.appId,
                    dev,
                    object : IQApplicationInfoListener {
                        override fun onApplicationInfoReceived(p0: IQApp?) {
                            continuation.resumeWith(
                                Result.success(
                                    p0?.status?.toInstalledAppState()
                                        ?: GarminWatch.InstalledAppState.UNKNOWN
                                )
                            )
                        }

                        override fun onApplicationNotInstalled(p0: String?) {
                            continuation.resumeWith(Result.success(GarminWatch.InstalledAppState.NOT_INSTALLED))
                        }
                    })
            }
        }
    }

    override fun getData(): Flow<Any> {
        return callbackFlow {
            val listener =
                IQApplicationEventListener { p0, p1, p2, p3 ->
                    Log.d("TAGgarmin", "onMessageReceived WOW")
                    Log.d("TAGgarmin", "onMessageReceived: ${p2?.joinToString()}")
                    p2?.let { trySend(it).isSuccess }
                }
            connectIQ.registerForAppEvents(device, app, listener)
            awaitClose {
                connectIQ.unregisterForApplicationEvents(device, IQApp(""))
            }
        }
    }

    override suspend fun sendData(watchSendKeys: WatchSendKeys): Boolean {
        val message = watchSendKeys.mapString()
        Log.d("TAGgarmin", "to send ${message}")
        return suspendCancellableCoroutine { continuation ->
            Log.d("TAGgarmin", "go!!!!!")
            connectIQ.sendMessage(device, app, message, object : IQSendMessageListener {
                override fun onMessageStatus(
                    p0: IQDevice?,
                    p1: IQApp?,
                    p2: ConnectIQ.IQMessageStatus?
                ) {

                    Log.d("TAGgarmin", "sended")
                    Log.d("TAGgarmin", p2?.name ?: "cos nie ma nic")

                    when (p2) {
                        SUCCESS -> continuation.resumeWith(Result.success(true))
                        else -> continuation.resumeWith(Result.success(false))
                    }
                }
            })
        }
    }
}
