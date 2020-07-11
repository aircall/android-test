package io.aircall.android.domain.usecase.repositories

import dev.olog.flow.test.observer.test
import io.aircall.android.data.api.GitHubApi
import io.aircall.android.data.model.IssueData
import io.aircall.android.data.model.KotlinPublicRepositoryData
import io.aircall.android.domain.date.DateHelper
import io.aircall.android.domain.model.IssuesByWeek
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.domain.usecase.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTopKotlinPublicRepositoriesTest {

    @Before
    fun setUp() {
        mockkObject(DateHelper)
    }

    @Test
    fun `GetTopKotlinPublicRepositoriesUseCase emits List of KotlinPublicRepository when GitHubApi returns result`() {
        runBlocking {
            // Given
            val mockGitHubApi: GitHubApi = mockk(relaxed = true)
            every { DateHelper.getCalendar() } returns currentCalendar
            coEvery { mockGitHubApi.getTopKotlinPublicRepositories() } returns fakeTopKotlinPublicRepositoriesData

            // When
            val result = GetTopKotlinPublicRepositoriesUseCase(mockGitHubApi)()

            // Then
            result.test(this) {
                assertValues(Result.Loading, Result.Data(expectedTopKotlinPublicRepositories))
                assertValueCount(2)
                assertComplete()
            }
            coVerify { mockGitHubApi.getTopKotlinPublicRepositories() }
            confirmVerified(mockGitHubApi)
        }
    }

    @Test
    fun `GetTopKotlinPublicRepositoriesUseCase emits error when GitHubApi returns an error`() {
        runBlocking {
            // Given
            val mockGitHubApi: GitHubApi = mockk(relaxed = true)
            coEvery { mockGitHubApi.getTopKotlinPublicRepositories() } throws fakeError

            // When
            val result = GetTopKotlinPublicRepositoriesUseCase(mockGitHubApi)()

            // Then
            result.test(this) {
                assertValues(Result.Loading, Result.Error(fakeError))
                assertValueCount(2)
                assertComplete()
            }
            coVerify { mockGitHubApi.getTopKotlinPublicRepositories() }
            confirmVerified(mockGitHubApi)
        }
    }

    companion object {
        private val currentCalendar = DateHelper.getCalendar()
        private val issueCalendar = DateHelper.getCalendar()

        init {
            currentCalendar.set(2020, 6, 11)
            issueCalendar.set(2020, 2, 11)
        }

        val fakeTopKotlinPublicRepositoriesData = listOf(
            KotlinPublicRepositoryData("fake", 50, 100, 30, 15, listOf(IssueData(issueCalendar.time)))
        )
        val expectedTopKotlinPublicRepositories = listOf(
            KotlinPublicRepository("fake", "50", "100", "30", "15", listOf(IssuesByWeek("2020-03-09", "2020-03-15", "1")))
        )

        val fakeError = Throwable("fake")
    }
}