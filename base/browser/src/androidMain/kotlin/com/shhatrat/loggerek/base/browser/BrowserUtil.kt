package com.shhatrat.loggerek.base.browser

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class BrowserUtil(private val context: Context) {
    actual fun openWithUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }
}