package io.aircall.android.domain.usecase.repositories

import io.aircall.android.data.api.GitHubApi
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.domain.toDomainObject
import io.aircall.android.domain.usecase.Result
import io.aircall.android.domain.usecase.UseCase
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopKotlinPublicRepositoriesUseCase @Inject constructor(private val gitHubApi: GitHubApi) :
    UseCase<List<KotlinPublicRepository>, Throwable>() {
    override suspend fun run() = flow {
        emit(Result.Loading)
        try {
            val topKotlinPublicRepositories = gitHubApi.getTopKotlinPublicRepositories().toDomainObject()
            emit(Result.Data(topKotlinPublicRepositories))
        } catch (throwable: Throwable) {
            emit(Result.Error(throwable))
        }
    }
}