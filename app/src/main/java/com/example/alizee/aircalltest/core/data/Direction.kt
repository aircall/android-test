package com.example.alizee.aircalltest.core.data

import com.google.gson.annotations.SerializedName

enum class Direction {
    @SerializedName("inbound")
    INBOUND,
    @SerializedName("outbound")
    OUTBOUND
}