package io.aircall.android.data.auth

import android.content.Intent

interface AuthManager {
    fun createAuthIntent(): Intent

    fun processAuthResult(data: Intent,
                          successCallback: () -> Unit,
                          errorCallback: (error: Throwable) -> Unit)

    fun processActionWithFreshAccessToken(action: (token: String) -> Unit)
}