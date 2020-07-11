package io.aircall.android.domain.usecase.user

import io.aircall.android.data.api.GitHubApi
import io.aircall.android.data.auth.AuthStateRepository
import io.aircall.android.domain.exception.UserNotAuthenticated
import io.aircall.android.domain.model.User
import io.aircall.android.domain.usecase.Result
import io.aircall.android.domain.usecase.UseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val gitHubApi: GitHubApi,
                                         private val authStateRepository: AuthStateRepository) : UseCase<User, Throwable>() {
    override suspend fun run() = flow {
        emit(Result.Loading)
        try {
            val user = authStateRepository.read()
                .takeIf { it.isAuthorized }
                ?.run {
                    gitHubApi.getUser()?.let {
                        User(it.name)
                    }
                } ?: throw UserNotAuthenticated
            emit(Result.Data(user))
        } catch (throwable: Throwable) {
            emit(Result.Error(throwable))
        }
    }
}