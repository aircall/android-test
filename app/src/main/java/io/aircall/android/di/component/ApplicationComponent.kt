package io.aircall.android.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.aircall.android.MainApplication
import io.aircall.android.di.module.ActivityModule
import io.aircall.android.di.module.ApplicationModule
import io.aircall.android.di.module.AuthModule
import io.aircall.android.di.module.ApiModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ActivityModule::class,
        AuthModule::class,
        ApiModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<MainApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: MainApplication)
}