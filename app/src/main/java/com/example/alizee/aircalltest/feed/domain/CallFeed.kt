package com.example.alizee.aircalltest.feed.domain

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.data.Action
import com.example.alizee.aircalltest.core.data.Direction
import com.example.alizee.aircalltest.core.data.domain.Call

data class CallFeed(val id: String,
                    val date: String,
                    val hour: String,
                    @DrawableRes val direction: Int?,
                    val interlocutor: String,
                    val via: String,
                    @StringRes val action: Int?)

fun Call.toCallFeed(): CallFeed {
    val direction = when (direction) {
        Direction.INBOUND -> R.drawable.ic_inbound
        Direction.OUTBOUND -> R.drawable.ic_outbound
        else -> null
    }
    val action = when (callType) {
        Action.ANSWERED -> R.string.answered
        Action.MISSED -> R.string.missed
        Action.VOICEMAIL -> R.string.voicemail
        else -> null
    }

    return CallFeed(id, date, hour, direction, interlocutor, via, action)
}