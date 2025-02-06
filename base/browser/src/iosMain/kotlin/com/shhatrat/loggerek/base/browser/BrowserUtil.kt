package com.shhatrat.loggerek.base.browser

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class BrowserUtil : IBrowserUtil{
    override fun openWithUrl(url: String) {
        val nsUrl = NSURL.URLWithString(url)
        if (nsUrl != null) {
            UIApplication.sharedApplication.openURL(nsUrl).let {
                UIApplication.sharedApplication.openURL(
                    nsUrl,
                    options = emptyMap<Any?, Any>() as Map<Any?, Any>,
                    completionHandler = null
                )
            }
        }
    }
}