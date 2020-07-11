package io.aircall.android.di.injector

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.aircall.android.presentation.main.MainFragment

@Module
abstract class MainInjector{
    @ContributesAndroidInjector
    abstract fun provideMainFragment(): MainFragment
}