package com.shhatrat.loggerek.manager.watch.logic

import android.content.Context
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.manager.watch.GarminWatch
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys.GET_DATA.parseWatchRetrieveKeys
import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import com.shhatrat.loggerek.manager.watch.model.toWatchCache
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GarminLogicImpl(
    private val context: Context,
    private val repository: Repository,
    private val api: Api,
    private val garminManager: GarminWatch,
    private val accountManager: AccountManager,
) : BaseWatchLogicImpl(context, repository, api) {


    override suspend fun isAvailable(): Boolean {
        garminManager.init()
        return isPossible()
    }

    private suspend fun isPossible(): Boolean {
        return accountManager.isUserLogged()
                && repository.garminIdentifier.get() != null
                && garminManager.getListOfDevices().map { it.identifier }
            .contains(repository.garminIdentifier.get())
                && hasLocationPermissions(context)
    }

    override fun start() {
        mainJob?.cancel()
        mainJob = scope.launch(Dispatchers.Main) {
            if (isAvailable()) {
                garminManager.selectDevice(
                    garminManager.getListOfDevices()
                        .first { it.identifier == repository.garminIdentifier.get() })
                garminManager.loadApp()
                sendData()
                garminManager.getData().collect {
                    when ((it as List<Map<String,String>>).first().parseWatchRetrieveKeys()) {
                        WatchRetrieveKeys.GET_DATA -> {
                            sendData()
                        }
                        is WatchRetrieveKeys.SET_LOG -> {
                            handleSendingLog((it.first().parseWatchRetrieveKeys() as WatchRetrieveKeys.SET_LOG))
                        }
                    }
                }
            }
        }
    }


    private suspend fun sendData() {
        val caches = getNearestCaches()
        garminManager.sendData(
            WatchSendKeys.GET_DATA(
                WatchData(
                    items = caches.map { it.toWatchCache() },
                    logs = repository.logs.get().map { WatchLog(logId = it.id, type = it.type, text = it.comment) }
                )
            )
        )
    }

    override fun stop() {
        mainJob?.cancel()
    }
}