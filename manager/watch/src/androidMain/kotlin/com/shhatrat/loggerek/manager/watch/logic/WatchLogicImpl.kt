package com.shhatrat.loggerek.manager.watch.logic

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.SubmitLogData
import com.shhatrat.loggerek.manager.watch.GarminWatch
import com.shhatrat.loggerek.manager.watch.LocationService
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys.GET_DATA.parseWatchRetrieveKeys
import com.shhatrat.loggerek.manager.watch.model.WatchSendKeys
import com.shhatrat.loggerek.manager.watch.model.toWatchCache
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class WatchLogicImpl(
    private val context: Context,
    private val repository: Repository,
    private val api: Api,
    private val garminManager: GarminWatch,
    private val accountManager: AccountManager,
) : WatchLogic {

    private val scope = CoroutineScope(Dispatchers.IO)


    override suspend fun isAvailable(): Boolean {
        garminManager.init()
        return hasLocationPermissions(context) && isPossible()
    }

    private suspend fun isPossible(): Boolean {
        return accountManager.isUserLogged()
                && repository.garminIdentifier.get() != null
                && garminManager.getListOfDevices().map { it.identifier }
            .contains(repository.garminIdentifier.get())
                && hasLocationPermissions(context)
    }


    private fun hasLocationPermissions(context: Context): Boolean {
        val fineLocation = context.checkPermission(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.os.Process.myPid(),
            android.os.Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocation = context.checkPermission(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.os.Process.myPid(),
            android.os.Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
        return fineLocation || coarseLocation
    }


    override fun start() {
        scope.launch(Dispatchers.Main) {
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

    private suspend fun handleSendingLog(setLog: WatchRetrieveKeys.SET_LOG) {
        val cache = api.getFullCache(setLog.cacheId, repository.token.get()!!, repository.tokenSecret.get()!!)
        val log = repository.logs.get().first { it.id == setLog.logId }
        val logData =SubmitLogData(
            cacheId = cache.code,
            logType = LogType.entries.first { it.apiKey == log.type },
            rating = null,
            comment = log.comment,
            reccomend = false,
            password = null
        )
        api.submitLog(logData, repository.token.get()!!, repository.tokenSecret.get()!!)
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

    private suspend fun getNearestCaches(): List<Geocache> {
        val tokenData = repository.safeTokenAndTokenSecret()
        return api.nearestGeocaches(
            getLocation().toApiString(),
            10,
            tokenData.token,
            tokenData.tokenSecret
        )
    }

    private fun Location.toApiString() = "${this.latitude}|${this.longitude}"

    private suspend fun getLocation(): Location =
        LocationService.getLocationFlow(context).first()

    override fun stop() {
        scope.cancel()
    }
}