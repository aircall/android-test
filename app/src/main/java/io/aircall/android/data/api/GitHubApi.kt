package io.aircall.android.data.api

import io.aircall.android.data.model.UserData

interface GitHubApi {
    suspend fun getUser(): UserData?
}