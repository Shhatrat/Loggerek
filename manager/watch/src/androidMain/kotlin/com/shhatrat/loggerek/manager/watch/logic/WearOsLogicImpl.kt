package com.shhatrat.loggerek.manager.watch.logic

import android.content.Context
import android.net.Uri
import android.util.Log
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import com.shhatrat.loggerek.manager.watch.model.toWatchCache
import com.shhatrat.loggerek.repository.Repository
import com.shhatrat.wearshared.CommunicationManager.observeDataFromWatch
import com.shhatrat.wearshared.CommunicationManager.sendDataToWatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WearOsLogicImpl(
    private val context: Context,
    private val repository: Repository,
    api: Api,
    private val accountManager: AccountManager
) : BaseWatchLogicImpl(context, repository, api) {

    override fun start() {
        mainJob?.cancel()
        mainJob = scope.launch(Dispatchers.Main) {
            if (isAvailable()) {
                sendData()
                observeDataFromWatch(context).collect {
                    var x: WatchRetrieveKeys.SET_LOG? = null
                    try {
                      x  = Json.decodeFromString<WatchRetrieveKeys.SET_LOG>(Uri.decode(it))
                    }catch (e :Exception){

                    }
                    if (it == "GET_DATA") {
                        sendData()
                    }
                    else if(x!=null){
                        handleSendingLog(x)                    }
                }
            }
        }
    }

    private suspend fun sendData() {
        val caches = getNearestCaches()
        sendDataToWatch(
            context,
            Uri.encode(Json.encodeToString(WatchData(
                items = caches.map { it.toWatchCache() },
                logs = repository.logs.get()
                    .map { WatchLog(logId = it.id, type = it.type, text = it.comment) })
            )))
    }


    override fun stop() {
        mainJob?.cancel()
    }

    override suspend fun isAvailable(): Boolean {
        return accountManager.isUserLogged()
                && hasLocationPermissions(context)
    }
}