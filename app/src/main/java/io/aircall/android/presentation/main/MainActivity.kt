package io.aircall.android.presentation.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import io.aircall.android.R

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}