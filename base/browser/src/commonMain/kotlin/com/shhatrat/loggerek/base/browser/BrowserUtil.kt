package com.shhatrat.loggerek.base.browser

expect class BrowserUtil: IBrowserUtil

interface IBrowserUtil {
    fun openWithUrl(url: String)

}