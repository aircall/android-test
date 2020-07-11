package io.aircall.android.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import io.aircall.android.TestApplication
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.di.builder.ViewModelFactory
import io.aircall.android.presentation.navigation.Navigator
import javax.inject.Singleton

@Module
class TestApplicationModule(private val viewModelFactory: ViewModelFactory,
                            private val navigator: Navigator,
                            private val authManager: AuthManager) {

    @Singleton
    @Provides
    fun provideContext(application: TestApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideViewModelFactory(): ViewModelProvider.Factory = viewModelFactory

    @Singleton
    @Provides
    fun provideNavigator(): Navigator = navigator

    @Singleton
    @Provides
    fun provideAuthManager(): AuthManager = authManager
}