package io.aircall.android.data.auth

import android.content.Intent
import net.openid.appauth.*


class OpenIdAuthManager(
    private val authService: AuthorizationService,
    private val clientAuth: ClientAuthentication,
    private val authStateRepository: AuthStateRepository
): AuthManager {

    override fun createAuthIntent(): Intent {
        val request =
            AuthorizationRequest
                .Builder(
                    AuthConfig.config,
                    AuthConfig.clientId,
                    ResponseTypeValues.CODE,
                    AuthConfig.redirectUri
                )
                .setScope(AuthConfig.scope)
                .build()

        return authService.getAuthorizationRequestIntent(request)
    }

    override fun processAuthResult(data: Intent, authResultProcessedCallback: () -> Unit) {
        AuthorizationResponse.fromIntent(data)?.let { authorizationResponse ->
            authService.performTokenRequest(
                authorizationResponse.createTokenExchangeRequest(),
                clientAuth
            ) { tokenResponse, exception ->
                authStateRepository.update(tokenResponse, exception)
                authResultProcessedCallback()
            }
        }
    }

    override fun processActionWithFreshAccessToken(action: (token: String) -> Unit) {
        authStateRepository.read()
            .performActionWithFreshTokens(authService) { accessToken: String?, _: String?, _: AuthorizationException? ->
                accessToken?.run { action(this) }
            }
    }
}