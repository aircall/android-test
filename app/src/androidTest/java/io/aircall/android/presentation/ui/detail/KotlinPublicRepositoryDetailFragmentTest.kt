package io.aircall.android.presentation.ui.detail

import android.content.Intent
import android.os.Bundle
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
import io.aircall.android.domain.model.IssuesByWeek
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.presentation.navigation.Navigator
import io.aircall.android.recyclerViewItemAtPositionMatches
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KotlinPublicRepositoryDetailFragmentTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TestActivity::class.java, false, false)

    private val kotlinPublicRepositoryDetailFragment = KotlinPublicRepositoryDetailFragment()
    private val kotlinPublicRepositoryLiveData = MediatorLiveData<KotlinPublicRepository>()

    @MockK
    lateinit var kotlinPublicRepositoryDetailViewModel: KotlinPublicRepositoryDetailViewModel

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
        kotlinPublicRepositoryDetailFragment.arguments = fakeArgs

        every { kotlinPublicRepositoryDetailViewModel.kotlinPublicRepositoryLiveData } returns kotlinPublicRepositoryLiveData
        every { viewModelFactory.create(KotlinPublicRepositoryDetailViewModel::class.java) } returns kotlinPublicRepositoryDetailViewModel
    }

    @Test
    fun testTopKotlinPublicRepositoryDetailIsDisplayedWhenDataIsReceived() {
        // Given
        kotlinPublicRepositoryLiveData.postValue(fakeKotlinPublicRepository)

        // When
        activityTestRule.launchActivity(Intent())
        activityTestRule.activity.setFragment(kotlinPublicRepositoryDetailFragment)

        // Then
        fakeKotlinPublicRepository.issuesByWeek.forEachIndexed { index, _ ->
            val expectedStartDate = fakeKotlinPublicRepository.issuesByWeek[index].startDate
            val expectedEndDate = fakeKotlinPublicRepository.issuesByWeek[index].endDate
            val expectedCount = fakeKotlinPublicRepository.issuesByWeek[index].count

            onView(withId(R.id.repositoryIssuesRecyclerView))
                .check(matches(isDisplayed()))
                .check(
                    matches(
                        recyclerViewItemAtPositionMatches(
                            index,
                            withText(String.format(activityTestRule.activity.getString(R.string.repository_issues), expectedCount, expectedStartDate, expectedEndDate)),
                            R.id.kotlinPublicRepositoryName
                        )
                    )
                )
        }
    }

    companion object FakeData {
        val fakeKotlinPublicRepository = KotlinPublicRepository(
            "fake",
            "800",
            "80",
            "15",
            "12",
            listOf(IssuesByWeek("2020-06-29", "2020-07-05", "3"))
        )
        val fakeArgs = Bundle()

        init {
            fakeArgs.putParcelable("kotlinPublicRepository", fakeKotlinPublicRepository)
        }
    }
}
