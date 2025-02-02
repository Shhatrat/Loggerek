package com.shhatrat.loggerek.base.browser

import java.awt.Desktop
import java.net.URI

actual class BrowserUtil {
    actual fun openWithUrl(url: String) {
        Desktop.getDesktop().browse(URI(url))
    }
}