package io.aircall.android.data.api

import io.aircall.android.GetTopKotlinPublicRepositoriesQuery
import io.aircall.android.GetUserQuery
import io.aircall.android.data.model.IssueData
import io.aircall.android.data.model.KotlinPublicRepositoryData
import io.aircall.android.data.model.UserData
import io.aircall.android.domain.date.DateHelper.toDate


fun GetUserQuery.Data.toDataResponse(): UserData? = UserData(this.viewer.login)

fun GetTopKotlinPublicRepositoriesQuery.AsRepository.toDataResponse() =
    KotlinPublicRepositoryData(
        name,
        stargazers.totalCount,
        watchers.totalCount,
        forkCount,
        pullRequests.totalCount,
        issues.toDataResponse()
    )

fun GetTopKotlinPublicRepositoriesQuery.Issues.toDataResponse() =
    nodes?.mapNotNull {
        it?.createdAt.toString().toDate()?.let { dateTime -> IssueData(dateTime) }
    }
        ?: emptyList()