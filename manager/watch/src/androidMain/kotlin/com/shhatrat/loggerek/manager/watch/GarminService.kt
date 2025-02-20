package com.shhatrat.loggerek.manager.watch

import android.content.Context
import android.content.pm.PackageManager
import com.shhatrat.loggerek.account.AccountManager
import com.shhatrat.loggerek.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

//TODO
class GarminService(
    private val context: Context,
//    private val repository: Repository,
//    private val garminManager: GarminManager,
//    private val accountManager: AccountManager
): IGarminService {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var mainJob: Job? = null

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


//    private suspend fun isPossible(): Boolean{
//        return accountManager.isUserLogged()
//                && repository.garminIdentifier.get() != null
//                && garminManager.getListOfDevices().map { it.identifier }.contains(repository.garminIdentifier.get())
//                && hasLocationPermissions(context)
//    }


    fun observe() {
        mainJob = scope.launch(Dispatchers.Main) {
//            garminManager.init()
//            garminManager.getData().collect {
//            }
            LocationService.getLocationFlow(context).collect{
                println("${it.time} -- ${it.latitude} ${it.longitude} ${it.accuracy}")
            }
        }
    }
}