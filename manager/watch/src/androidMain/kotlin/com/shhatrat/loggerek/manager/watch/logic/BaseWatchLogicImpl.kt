package com.shhatrat.loggerek.manager.watch.logic

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import com.shhatrat.loggerek.api.Api
import com.shhatrat.loggerek.api.model.Geocache
import com.shhatrat.loggerek.api.model.LogType
import com.shhatrat.loggerek.api.model.SubmitLogData
import com.shhatrat.loggerek.manager.watch.LocationService
import com.shhatrat.loggerek.manager.watch.model.WatchRetrieveKeys
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first

abstract class BaseWatchLogicImpl(
    private val context: Context,
    private val repository: Repository,
    private val api: Api): WatchLogic {

    protected var mainJob: Job? = null

    protected val scope = CoroutineScope(Dispatchers.IO)

    internal fun hasLocationPermissions(context: Context): Boolean {
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

    protected suspend fun handleSendingLog(setLog: WatchRetrieveKeys.SET_LOG) {
        val cache = api.getFullCache(setLog.cacheId, repository.token.get()!!, repository.tokenSecret.get()!!)
        val log = repository.logs.get().first { it.id == setLog.logId }
        val logData = SubmitLogData(
            cacheId = cache.code,
            logType = LogType.entries.first { it.apiKey == log.type },
            rating = null,
            comment = log.comment,
            reccomend = false,
            password = null,
            date = null
        )
        api.submitLog(logData, repository.token.get()!!, repository.tokenSecret.get()!!)
    }

    protected suspend fun getNearestCaches(): List<Geocache> {
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

}