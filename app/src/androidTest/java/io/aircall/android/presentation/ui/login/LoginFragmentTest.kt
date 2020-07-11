package io.aircall.android.presentation.ui.login

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.aircall.android.R
import io.aircall.android.TestActivity
import io.aircall.android.TestInjector
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.di.builder.ViewModelFactory
import io.aircall.android.di.module.TestApplicationModule
import io.aircall.android.presentation.navigation.Navigator
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TestActivity::class.java, false, false)

    private val loginFragment = LoginFragment()

    @MockK
    lateinit var viewModelFactory: ViewModelFactory

    @MockK
    lateinit var authManager: AuthManager

    @MockK
    lateinit var navigator: Navigator

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        TestInjector(TestApplicationModule(viewModelFactory, navigator, authManager)).inject()
        every { navigator.startAuth(loginFragment, any()) } just runs
    }

    @Test
    fun testClickOnButtonLaunchAuth() {
        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(loginFragment)
        onView(withId(R.id.loginButton))
            .perform(click())

        // Then
        verify { navigator.startAuth(loginFragment, any()) }
    }
}
