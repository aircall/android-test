package io.aircall.android.domain.usecase.repositories

import dev.olog.flow.test.observer.test
import io.aircall.android.data.api.GitHubApi
import io.aircall.android.data.model.KotlinPublicRepositoryData
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.domain.usecase.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTopKotlinPublicRepositoriesTest {

    @Test
    fun `GetTopKotlinPublicRepositoriesUseCase emits List of KotlinPublicRepository when GitHubApi returns result`() {
        runBlocking {
            // Given
            val mockGitHubApi: GitHubApi = mockk(relaxed = true)
            coEvery { mockGitHubApi.getTopKotlinPublicRepositories() } returns fakeTopKotlinPublicRepositoriesData

            // When
            val result = GetTopKotlinPublicRepositoriesUseCase(mockGitHubApi)()

            // Then
            result.test(this) {
                val expectedData = fakeTopKotlinPublicRepositoriesData.map { KotlinPublicRepository(it.name) }
                assertValues(Result.Loading, Result.Data(expectedData))
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

    companion object FakeData {
        val fakeTopKotlinPublicRepositoriesData = listOf(KotlinPublicRepositoryData("fake"))
        val fakeError = Throwable("fake")
    }
}