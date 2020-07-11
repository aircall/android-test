package io.aircall.android.domain.usecase.user

import dev.olog.flow.test.observer.test
import io.aircall.android.data.api.GitHubApi
import io.aircall.android.data.auth.AuthStateRepository
import io.aircall.android.data.model.UserData
import io.aircall.android.domain.exception.UserNotAuthenticated
import io.aircall.android.domain.model.User
import io.aircall.android.domain.usecase.Result
import io.mockk.*
import kotlinx.coroutines.runBlocking
import net.openid.appauth.AuthState
import org.junit.Test
import java.lang.Exception

class GetUserUseCaseTest {

    @Test
    fun `GetUserUseCase emits User when User is authorized`() {
        runBlocking {
            // Given
            val mockAuthState: AuthState = mockk(relaxed = true)
            val mockAuthStateRepository: AuthStateRepository = mockk(relaxed = true)
            val mockGitHubApi: GitHubApi = mockk(relaxed = true)
            every { mockAuthStateRepository.read() } returns mockAuthState
            every { mockAuthState.isAuthorized } returns true
            coEvery { mockGitHubApi.getUser() } returns fakeUserData

            // When
            val result = GetUserUseCase(mockGitHubApi, mockAuthStateRepository)()

            // Then
            result.test(this) {
                assertValues(Result.Loading, Result.Data(User(fakeUserData.name)))
                assertValueCount(2)
                assertComplete()
            }
            coVerify { mockGitHubApi.getUser() }
            confirmVerified(mockGitHubApi)
        }
    }

    @Test
    fun `GetUserUseCase emits UserNotAuthenticated when User is not authorized`() {
        runBlocking {
            // Given
            val mockAuthState: AuthState = mockk(relaxed = true)
            val mockAuthStateRepository: AuthStateRepository = mockk(relaxed = true)
            val mockGitHubApi: GitHubApi = mockk(relaxed = true)
            every { mockAuthStateRepository.read() } returns mockAuthState
            every { mockAuthState.isAuthorized } returns false
            coEvery { mockGitHubApi.getUser() } returns fakeUserData

            // When
            val result = GetUserUseCase(mockGitHubApi, mockAuthStateRepository)()

            // Then
            result.test(this) {
                assertValues(Result.Loading, Result.Error(UserNotAuthenticated))
                assertValueCount(2)
                assertComplete()
            }
            coVerify(exactly = 0) { mockGitHubApi.getUser() }
            confirmVerified(mockGitHubApi)
        }
    }

    @Test
    fun `GetUserUseCase emits error when GitHubApi returns an error`() {
        runBlocking {
            // Given
            val mockAuthState: AuthState = mockk(relaxed = true)
            val mockAuthStateRepository: AuthStateRepository = mockk(relaxed = true)
            val mockGitHubApi: GitHubApi = mockk(relaxed = true)
            every { mockAuthStateRepository.read() } returns mockAuthState
            every { mockAuthState.isAuthorized } returns true
            coEvery { mockGitHubApi.getUser() } throws fakeError

            // When
            val result = GetUserUseCase(mockGitHubApi, mockAuthStateRepository)()

            // Then
            result.test(this) {
                assertValues(Result.Loading, Result.Error(fakeError))
                assertValueCount(2)
                assertComplete()
            }
            coVerify { mockGitHubApi.getUser() }
            confirmVerified(mockGitHubApi)
        }
    }

    companion object FakeData {
        val fakeUserData = UserData("Luke")
        val fakeError = Throwable("fake")
    }
}