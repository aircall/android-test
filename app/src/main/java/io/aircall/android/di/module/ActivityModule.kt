package io.aircall.android.di.module

import io.aircall.android.di.injector.MainInjector
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.aircall.android.presentation.main.MainActivity

@Module(includes = [AndroidSupportInjectionModule::class])
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            MainInjector::class
        ]
    )
    fun mainActivityInjector(): MainActivity
}