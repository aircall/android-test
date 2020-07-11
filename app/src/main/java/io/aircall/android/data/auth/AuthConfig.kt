package io.aircall.android.data.auth

import android.net.Uri
import net.openid.appauth.AuthorizationServiceConfiguration

object AuthConfig {
    private val authEndpoint = Uri.parse("https://github.com/login/oauth/authorize")
    private val tokenEndpoint = Uri.parse("https://github.com/login/oauth/access_token")
    const val clientId = "530b1bc956c6fb1bc85e"
    const val clientSecret = "8991666a64986be20382f43c08ebb357605102b0"
    const val scope = "read:user"
    val redirectUri: Uri = Uri.parse("io.aircall.android://callback")
    val config = AuthorizationServiceConfiguration(authEndpoint, tokenEndpoint)
}