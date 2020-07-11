package io.aircall.android.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.aircall.android.TestApplication
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.di.builder.ViewModelFactory
import io.aircall.android.presentation.navigation.IntentLauncher
import javax.inject.Singleton

@Module
class TestApplicationModule(private val viewModelFactory: ViewModelFactory,
                            private val intentLauncher: IntentLauncher,
                            private val authManager: AuthManager) {

    @Singleton
    @Provides
    fun provideContext(application: TestApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory = viewModelFactory

    @Singleton
    @Provides
    fun provideIntentLauncher(): IntentLauncher = intentLauncher

    @Singleton
    @Provides
    fun provideAuthManager(): AuthManager = authManager
}