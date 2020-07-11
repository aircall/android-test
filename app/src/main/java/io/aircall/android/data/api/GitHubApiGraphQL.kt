package io.aircall.android.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.toDeferred
import io.aircall.android.GetUserQuery
import io.aircall.android.data.model.UserData

class GitHubApiGraphQL(
    private val apolloClient: ApolloClient
) : GitHubApi {

    override suspend fun getUser() =
        apolloClient
            .query(GetUserQuery())
            .toDeferred()
            .await()
            .data
            ?.let { UserData(it.viewer.login) }
}