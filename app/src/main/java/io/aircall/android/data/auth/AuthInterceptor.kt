package io.aircall.android.data.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authManager: AuthManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .let { builder ->
                authManager.processActionWithFreshAccessToken { accessToken ->
                    builder.apply {
                        addHeader("Authorization", "bearer $accessToken")
                    }
                }
                builder.build()
            }

        return chain.proceed(request)
    }
}