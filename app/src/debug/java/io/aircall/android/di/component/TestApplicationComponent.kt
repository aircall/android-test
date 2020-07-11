package io.aircall.android.di.component

import io.aircall.android.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.aircall.android.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestApplicationModule::class,
        TestActivityModule::class
    ]
)
interface TestApplicationComponent : AndroidInjector<TestApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TestApplication): Builder

        fun appModule(appModule: TestApplicationModule): Builder

        fun build(): TestApplicationComponent
    }

    override fun inject(application: TestApplication)
}