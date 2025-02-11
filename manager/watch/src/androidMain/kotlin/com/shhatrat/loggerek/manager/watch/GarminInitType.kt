package com.shhatrat.loggerek.manager.watch

import com.garmin.android.connectiq.ConnectIQ

enum class GarminInitType(
    val type: ConnectIQ.IQConnectType,
    val appId: String
) {

    REAL(
        type = ConnectIQ.IQConnectType.WIRELESS,
        appId = "10b02fda-7fd6-4174-a292-0602f1eb9886"
    ),
    FAKE(
        type = ConnectIQ.IQConnectType.TETHERED,
        appId = ""
    )
}
