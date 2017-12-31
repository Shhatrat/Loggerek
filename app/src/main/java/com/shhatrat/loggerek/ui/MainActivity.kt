package com.shhatrat.loggerek.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.shhatrat.loggerek.R
import com.shhatrat.loggerek.ui.ex.ExActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity<ExActivity>()
    }
}
