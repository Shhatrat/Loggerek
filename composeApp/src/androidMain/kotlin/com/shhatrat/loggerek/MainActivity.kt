package com.shhatrat.loggerek

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.shhatrat.base.BaseData
import com.shhatrat.base.startValidService
import com.shhatrat.loggerek.base.WindowSizeCallback
import com.shhatrat.loggerek.manager.watch.service.GarminBackgroundService
import org.koin.android.ext.koin.androidContext
import org.koin.compose.getKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

class MainActivity : ComponentActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        startServiceIfRequired(intent)
    }

    private fun startServiceIfRequired(intent: Intent) {
        val baseData by inject<BaseData>(BaseData::class.java)
        if(intent.hasExtra(baseData.notificationKey())) {
            startValidService(Intent(this, GarminBackgroundService::class.java))
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            getKoin().loadModules(listOf(
                module {
                    single<WindowSizeCallback> { { calculateWindowSizeClass(this@MainActivity) } }
                }))
            App(
                calculateWindowSizeClass = { calculateWindowSizeClass(this) },
                additionalKoinConfig = { this.androidContext(applicationContext) },
                startKoin = false
            )
        }
    }
}
