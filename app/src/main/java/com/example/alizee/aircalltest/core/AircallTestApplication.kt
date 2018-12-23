package com.example.alizee.aircalltest.core

import android.app.Application
import com.example.alizee.aircalltest.core.di.AppComponent
import com.example.alizee.aircalltest.core.di.AppModule
import com.example.alizee.aircalltest.core.di.DaggerAppComponent
import com.facebook.stetho.Stetho

class AircallTestApplication : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        setup()
    }

    private fun setup() {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
        instance = this
    }

    fun getComponent(): AppComponent = component

    companion object {
        lateinit var instance: AircallTestApplication private set
    }
}

