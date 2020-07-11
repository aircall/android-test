package io.aircall.android.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.aircall.android.GetTopKotlinPublicRepositoriesQuery
import io.aircall.android.GetUserQuery

class GitHubApiGraphQL(
    private val apolloClient: ApolloClient
) : GitHubApi {

    override suspend fun getUser() =
        apolloClient
            .query(GetUserQuery())
            .toDeferred()
            .await()
            .data
            ?.toDataResponse()

    override suspend fun getTopKotlinPublicRepositories() =
        apolloClient
            .query(GetTopKotlinPublicRepositoriesQuery())
            .toDeferred()
            .await()
            .data?.search?.edges?.mapNotNull { edge ->
                edge?.node?.asRepository?.toDataResponse()
            }
            ?: emptyList()
}