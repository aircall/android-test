package io.aircall.android

import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.DaggerApplication
import io.aircall.android.di.component.DaggerApplicationComponent


class MainApplication : DaggerApplication() {

    private val applicationInjector = DaggerApplicationComponent.builder()
        .application(this)
        .build()

    override fun applicationInjector() = applicationInjector

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}