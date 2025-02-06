package com.shhatrat.loggerek.base.browser

import kotlinx.browser.window

actual class BrowserUtil: IBrowserUtil {
    override fun openWithUrl(url: String) {
        window.open(url, "_blank")
    }
}