package io.aircall.android.presentation.main

import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.aircall.android.R
import io.aircall.android.TestActivity
import io.aircall.android.TestInjector
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.di.builder.ViewModelFactory
import io.aircall.android.di.module.TestApplicationModule
import io.aircall.android.domain.model.User
import io.aircall.android.presentation.navigation.IntentLauncher
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TestActivity::class.java, false, false)

    private val mainFragment = MainFragment()
    private val authStateLiveData = MediatorLiveData<MainViewModel.AuthState>()
    private val userLiveData = MediatorLiveData<User>()
    private val errorLiveData = MediatorLiveData<Throwable>()

    @MockK
    lateinit var mainViewModel: MainViewModel

    @MockK
    lateinit var viewModelFactory: ViewModelFactory

    @MockK
    lateinit var authManager: AuthManager

    @MockK
    lateinit var intentLauncher: IntentLauncher

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true, relaxUnitFun = true)
        TestInjector(TestApplicationModule(viewModelFactory, intentLauncher, authManager)).inject()

        every { mainViewModel.authStateLiveData } returns authStateLiveData
        every { mainViewModel.userLiveData } returns userLiveData
        every { mainViewModel.errorLiveData } returns errorLiveData
        every { viewModelFactory.create(MainViewModel::class.java) } returns mainViewModel
        every { intentLauncher.startAuth(mainFragment, any()) } just runs
        every { authManager.createAuthIntent() } returns fakeIntent
    }

    @Test
    fun testAuthStateUnlogged() {
        // Given
        authStateLiveData.postValue(MainViewModel.AuthState.UNLOGGED)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(mainFragment)

        // Then
        onView(withId(R.id.welcomeMessage))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testAuthStateLogged() {
        // Given
        authStateLiveData.postValue(MainViewModel.AuthState.LOGGED)
        userLiveData.postValue(fakeUser)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(mainFragment)

        // Then
        onView(withId(R.id.welcomeMessage))
            .check(matches(isDisplayed()))
            .check(matches(withText(mainFragment.getString(R.string.welcome_message).format(fakeUser.name))))
        onView(withId(R.id.loginButton))
            .check(matches(not(isDisplayed())))
    }

    @Test
    fun testErrorDisplayedInSnackbar() {
        // Given
        errorLiveData.postValue(fakeError)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(mainFragment)

        // Then
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(fakeError.message)))
    }

    @Test
    fun testClickOnButtonLaunchAuth() {
        // Given
        authStateLiveData.postValue(MainViewModel.AuthState.UNLOGGED)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(mainFragment)
        onView(withId(R.id.loginButton))
            .perform(click())

        // Then
        verify { intentLauncher.startAuth(mainFragment, any()) }
    }

    companion object FakeData {
        val fakeUser = User("Luke")
        val fakeError = Throwable("fake")
        val fakeIntent = Intent("fake")
    }
}
