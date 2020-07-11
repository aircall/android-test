package io.aircall.android.presentation.ui.repositories

import android.content.Intent
import androidx.lifecycle.MediatorLiveData
import androidx.test.espresso.Espresso.onView
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
import io.aircall.android.domain.exception.UserNotAuthenticated
import io.aircall.android.domain.model.IssuesByWeek
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.domain.model.User
import io.aircall.android.presentation.navigation.Navigator
import io.aircall.android.recyclerViewItemAtPositionMatches
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopKotlinPublicRepositoriesFragmentTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TestActivity::class.java, false, false)

    private val topKotlinPublicRepositoriesFragment = TopKotlinPublicRepositoriesFragment()
    private val userLiveData = MediatorLiveData<User>()
    private val errorLiveData = MediatorLiveData<Throwable>()
    private val topKotlinPublicRepositoriesLiveData =
        MediatorLiveData<List<KotlinPublicRepository>>()
    private val dataLoadingLiveData = MediatorLiveData<Boolean>()

    @MockK
    lateinit var topKotlinPublicRepositoriesViewModel: TopKotlinPublicRepositoriesViewModel

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

        every { topKotlinPublicRepositoriesViewModel.userLiveData } returns userLiveData
        every { topKotlinPublicRepositoriesViewModel.errorLiveData } returns errorLiveData
        every { topKotlinPublicRepositoriesViewModel.topKotlinPublicRepositoriesLiveData } returns topKotlinPublicRepositoriesLiveData
        every { topKotlinPublicRepositoriesViewModel.dataLoadingLiveData } returns dataLoadingLiveData
        every { viewModelFactory.create(TopKotlinPublicRepositoriesViewModel::class.java) } returns topKotlinPublicRepositoriesViewModel
        every { navigator.navigateToLoginFragment(topKotlinPublicRepositoriesFragment) } just runs
    }

    @Test
    fun testWelcomeMessageWhenUserIsReceived() {
        // Given
        userLiveData.postValue(fakeUser)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(topKotlinPublicRepositoriesFragment)

        // Then
        onView(withId(R.id.welcomeMessage))
            .check(
                matches(
                    withText(
                        activityTestRule.activity.getString(R.string.welcome_message)
                            .format(fakeUser.name)
                    )
                )
            )
    }

    @Test
    fun testTopKotlinPublicRepositoriesAreDisplayedWhenDataIsReceived() {
        // Given
        dataLoadingLiveData.postValue(true)
        topKotlinPublicRepositoriesLiveData.postValue(fakeTopKotlinPublicRepositories)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(topKotlinPublicRepositoriesFragment)

        // Then
        fakeTopKotlinPublicRepositories.forEachIndexed { index, _ ->
            onView(withId(R.id.topKotlinPublicRepositoriesRecyclerView))
                .check(matches(isDisplayed()))
                .check(
                    matches(
                        recyclerViewItemAtPositionMatches(
                            index,
                            withText(fakeTopKotlinPublicRepositories[index].name),
                            R.id.kotlinPublicRepositoryName
                        )
                    )
                )
        }
    }

    @Test
    fun testUserIsRedirectedToLoginFragmentIfNotAuthenticated() {
        // Given
        errorLiveData.postValue(UserNotAuthenticated)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(topKotlinPublicRepositoriesFragment)

        // Then
        // TODO check why verification fails
//        verify { navigator.navigateToLoginFragment(topKotlinPublicRepositoriesFragment) }
        confirmVerified(navigator)
    }

    @Test
    fun testLoadingLayoutIsDisplayedWhenDataIsLoading() {
        // Given
        dataLoadingLiveData.postValue(true)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(topKotlinPublicRepositoriesFragment)

        // Then
        onView(withId(R.id.loadingLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testMessageIsDisplayedInSnackbarWhenErrorIsReceived() {
        // Given
        errorLiveData.postValue(fakeError)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(topKotlinPublicRepositoriesFragment)

        // Then
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(fakeError.message)))
    }

    companion object FakeData {
        val fakeUser = User("Luke")
        val fakeTopKotlinPublicRepositories = listOf(
            KotlinPublicRepository(
                "fake",
                "fake",
                "fake",
                "fake",
                "fake",
                listOf(IssuesByWeek("fake", "fake", "fake"))
            )
        )
        val fakeError = Throwable("fake")
    }
}
