package io.aircall.android.data.auth

import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationException
import net.openid.appauth.TokenResponse

interface AuthStateRepository {
    fun read(): AuthState
    fun update(tokenResponse: TokenResponse?, exception: AuthorizationException?)
}