package io.aircall.android.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IssuesByWeek(val startDate: String,
                        val endDate: String,
                        val count: String) : Parcelable