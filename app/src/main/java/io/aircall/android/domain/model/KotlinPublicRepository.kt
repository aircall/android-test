package io.aircall.android.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KotlinPublicRepository(
    val name: String,
    val starsCount: String,
    val watchersCount: String,
    val forkCount: String,
    val prCount: String,
    val issuesByWeek: List<IssuesByWeek>
) : Parcelable