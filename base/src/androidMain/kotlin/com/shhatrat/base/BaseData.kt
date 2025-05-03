package com.shhatrat.base

interface BaseData {

    fun notificationKey() = "NotificationKey"

    fun getMainActivity(): Class<out Any>
}