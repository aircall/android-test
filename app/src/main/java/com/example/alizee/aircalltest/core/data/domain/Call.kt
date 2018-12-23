package com.example.alizee.aircalltest.core.data.domain

import com.example.alizee.aircalltest.core.data.cache.CallCache
import com.example.alizee.aircalltest.core.data.Action
import com.example.alizee.aircalltest.core.data.Direction

data class Call(
    val id: String,
    val date: String,
    val hour: String,
    val direction: Direction?,
    val interlocutor: String,
    val via: String,
    val callType: Action?
)


fun CallCache.toDomain(): Call {

    val domainDirection = when (direction) {
        Direction.OUTBOUND.name -> Direction.OUTBOUND
        Direction.INBOUND.name -> Direction.INBOUND
        else -> null
    }

    val domainAction = when (callType) {
        Action.MISSED.name -> Action.MISSED
        Action.VOICEMAIL.name -> Action.VOICEMAIL
        Action.ANSWERED.name -> Action.ANSWERED
        else -> null
    }

    return Call(
        id,
        date,
        hour,
        domainDirection,
        interlocutorNumber,
        via,
        domainAction
    )
}