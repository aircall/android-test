package io.aircall.android.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.di.builder.ViewModelFactoryBuilder
import io.aircall.android.presentation.navigation.IntentLauncher
import io.aircall.android.presentation.navigation.IntentLauncherImpl
import javax.inject.Singleton


@Module(includes = [ViewModelFactoryBuilder::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideIntentLauncher(authManager: AuthManager): IntentLauncher = IntentLauncherImpl(authManager)
}