package io.aircall.android.di.injector

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.aircall.android.presentation.ui.login.LoginFragment
import io.aircall.android.presentation.ui.repositories.TopKotlinPublicRepositoriesFragment

@Module
abstract class MainInjector{
    @ContributesAndroidInjector
    abstract fun provideMainFragment(): TopKotlinPublicRepositoriesFragment
    @ContributesAndroidInjector
    abstract fun provideLoginFragment(): LoginFragment
}