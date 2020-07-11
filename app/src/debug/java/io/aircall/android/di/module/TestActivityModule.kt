package io.aircall.android.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import io.aircall.android.TestActivity
import io.aircall.android.di.injector.MainInjector

@Module(includes = [AndroidSupportInjectionModule::class])
interface TestActivityModule {

    @ContributesAndroidInjector(
        modules = [
            MainInjector::class
        ]
    )
    fun testActivityInjector(): TestActivity

}