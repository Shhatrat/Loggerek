package com.shhatrat.loggerek.manager.watch

import android.content.Context
import android.util.Log
import com.garmin.android.connectiq.ConnectIQ
import com.garmin.android.connectiq.ConnectIQ.ConnectIQListener
import com.garmin.android.connectiq.ConnectIQ.IQApplicationEventListener
import com.garmin.android.connectiq.ConnectIQ.IQMessageStatus.*
import com.garmin.android.connectiq.ConnectIQ.IQSendMessageListener
import com.garmin.android.connectiq.IQApp
import com.garmin.android.connectiq.IQDevice
import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine

actual class GarminManager(private val context: Context): Watch {

    lateinit var connectIQ: ConnectIQ
    lateinit var device : IQDevice
    lateinit var app : IQApp

    override suspend fun init(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            connectIQ = ConnectIQ.getInstance(context, ConnectIQ.IQConnectType.TETHERED)
            connectIQ.initialize(context, true, object : ConnectIQListener {
                override fun onSdkReady() {
                    Log.d("TAGgarmin", "onSdkReady: Success")
                    device = connectIQ.knownDevices.first()
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

    override fun getData(): Flow<Any> {
        return callbackFlow {
            val listener =
                IQApplicationEventListener { p0, p1, p2, p3 ->
                    Log.d("TAGgarmin", "onMessageReceived WOW")
                    Log.d("TAGgarmin", "onMessageReceived: ${p2?.joinToString()}")
                    p2?.let { trySend(it).isSuccess }
                }
            connectIQ.registerForAppEvents(device, IQApp(""), listener)
            awaitClose {
                connectIQ.unregisterForApplicationEvents(device, IQApp(""))
            }
        }
    }

//             connectIQ.getApplicationInfo("10b02fda-7fd6-4174-a292-0602f1eb9886", it, object: IQApplicationInfoListener{
//                 override fun onApplicationInfoReceived(p0: IQApp?) {
//                     app = p0!!
//                     Log.d("TAGgarmin", "---> ${p0?.status}")
//                     Log.d("TAGgarmin", "---> ${p0?.applicationId}")
//                     Log.d("TAGgarmin", "---> ${p0?.displayName}")
//                     Log.d("TAGgarmin", "---> ${p0?.version()}")
//                     trySend(it, p0)
//                 }

    override suspend fun sendData(watchSendKeys: WatchSendKeys): Boolean{
        val message = watchSendKeys.mapString()
        return suspendCancellableCoroutine { continuation ->
            connectIQ.sendMessage(device, IQApp(""), message,  object :IQSendMessageListener{
                override fun onMessageStatus(
                    p0: IQDevice?,
                    p1: IQApp?,
                    p2: ConnectIQ.IQMessageStatus?
                ) {
                    when(p2){
                        SUCCESS -> continuation.resumeWith(Result.success(true))
                        else -> continuation.resumeWith(Result.success(false))
                    }
                }
            })
        }
    }
}
