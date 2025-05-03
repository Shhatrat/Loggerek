package com.shhatrat.base

import android.content.Context
import android.content.Intent
import android.os.Build

fun Context.startValidService(serviceIntent: Intent){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(serviceIntent)
    } else {
        startService(serviceIntent)
    }
}