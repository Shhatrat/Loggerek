package com.shhatrat.loggerek.presentation

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import com.shhatrat.loggerek.manager.watch.model.WatchData
import com.shhatrat.loggerek.manager.watch.model.WatchLog
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class NavigationScreen(val key: String) {
    data object INTRO: NavigationScreen("INTRO")
    data object CACHE_LIST: NavigationScreen("CACHE_LIST/{watchData}"){
        fun createRoute(watchData: WatchData): String{
            return "CACHE_LIST/${Uri.encode(Json.encodeToString(watchData))}"
        }

        fun encodeWatchData(args: NavBackStackEntry): WatchData{
            val json = args.arguments!!.getString("watchData")!!
            return Json.decodeFromString<WatchData>(Uri.decode(json))
        }
    }
    data object LOG_LIST: NavigationScreen("LOG_LIST/{cacheId}/{logs}"){
        fun createRoute(cacheId: String, logs: List<WatchLog>): String{
            return "LOG_LIST/$cacheId/${Uri.encode(Json.encodeToString(logs))}"
        }

        fun encodeWatchData(args: NavBackStackEntry): Pair<String,List<WatchLog>>{
            val json = args.arguments!!.getString("logs")!!
            return Pair(
                args.arguments!!.getString("cacheId")!!,
                Json.decodeFromString<List<WatchLog>>(Uri.decode(json)))
        }
    }
}