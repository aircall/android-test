package io.aircall.android.di.module


import com.apollographql.apollo.ApolloClient
import dagger.Module
import dagger.Provides
import io.aircall.android.data.api.GitHubApi
import io.aircall.android.data.api.GitHubApiGraphQL
import io.aircall.android.data.auth.AuthInterceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class ApiModule {

    @Singleton
    @Provides
    fun provideGitHubApi(apolloClient: ApolloClient): GitHubApi {
        return GitHubApiGraphQL(apolloClient)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
}