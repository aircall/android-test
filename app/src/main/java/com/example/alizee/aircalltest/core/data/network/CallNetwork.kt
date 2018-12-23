package com.example.alizee.aircalltest.core.data.network

import com.example.alizee.aircalltest.core.data.Action
import com.example.alizee.aircalltest.core.data.Direction
import com.google.gson.annotations.SerializedName
import java.util.*

data class CallNetwork(
    val id: String,
    @SerializedName("created_at") val createdAt: Date,
    val direction: Direction,
    val from: String,
    val to: String?,
    val via: String,
    val duration: String,
    @SerializedName("is_archived") val isArchived: Boolean,
    @SerializedName("call_type") val callType: Action
)