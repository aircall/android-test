package io.aircall.android.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import io.aircall.android.data.auth.*
import net.openid.appauth.AuthorizationService
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretBasic
import javax.inject.Singleton


@Module(includes = [ApplicationModule::class])
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthorizationService(
        context: Context
    ): AuthorizationService = AuthorizationService(context)

    @Singleton
    @Provides
    fun provideClientAuthentication(): ClientAuthentication =
        ClientSecretBasic(AuthConfig.clientSecret)

    @Singleton
    @Provides
    fun provideAuthManager(
        authorizationService: AuthorizationService,
        clientAuthentication: ClientAuthentication,
        authStateRepository: AuthStateRepository
    ): AuthManager = OpenIdAuthManager(authorizationService, clientAuthentication, authStateRepository)

    @Singleton
    @Provides
    fun provideAuthStateRepository(): AuthStateRepository = AuthStateRepositoryInMemory

    @Singleton
    @Provides
    fun provideAuthInterceptor(authManager: AuthManager): AuthInterceptor =
        AuthInterceptor(authManager)
}