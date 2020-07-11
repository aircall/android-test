package io.aircall.android

import androidx.test.platform.app.InstrumentationRegistry
import io.aircall.android.di.component.DaggerTestApplicationComponent
import io.aircall.android.di.module.TestApplicationModule

class TestInjector(private val testApplicationModule: TestApplicationModule) {

    fun inject() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TestApplication

        DaggerTestApplicationComponent
            .builder()
            .appModule(testApplicationModule)
            .application(app)
            .build()
            .inject(app)
    }
}