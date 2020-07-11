package io.aircall.android.data.api

import io.aircall.android.data.model.KotlinPublicRepositoryData
import io.aircall.android.data.model.UserData

interface GitHubApi {
    suspend fun getUser(): UserData?
    suspend fun getTopKotlinPublicRepositories(): List<KotlinPublicRepositoryData>
}