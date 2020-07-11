package io.aircall.android.data.auth

import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.TokenResponse

object AuthStateRepositoryInMemory: AuthStateRepository {
    private var authState = AuthState(AuthConfig.config)

    override fun read() = authState

    override fun update(tokenResponse: TokenResponse?, exception: AuthorizationException?) {
        authState.update(tokenResponse, exception)
    }
}