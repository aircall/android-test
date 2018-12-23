package com.example.alizee.aircalltest.core.data

import com.google.gson.annotations.SerializedName

enum class Action {
    @SerializedName("voicemail")
    VOICEMAIL,
    @SerializedName("answered")
    ANSWERED,
    @SerializedName("missed")
    MISSED
}