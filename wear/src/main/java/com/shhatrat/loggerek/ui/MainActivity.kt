package com.shhatrat.loggerek.ui

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.shhatrat.loggerek.R
import com.shhatrat.loggerek.ui.ex.ExActivity
import org.jetbrains.anko.startActivity


class MainActivity : WearableActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()
        startActivity<ExActivity>()
    }
}
