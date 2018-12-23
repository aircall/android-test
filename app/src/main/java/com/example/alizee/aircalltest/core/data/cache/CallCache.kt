package com.example.alizee.aircalltest.core.data.cache

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.example.alizee.aircalltest.core.data.domain.Call

@Entity(tableName = "call_table")
data class CallCache(
    @PrimaryKey val id: String,
    val date: String,
    val hour: String,
    val direction: String?,
    val interlocutorNumber: String,
    val via: String,
    val callType: String?
)

fun Call.toCache() = CallCache(
    id,
    date,
    hour,
    direction?.name,
    interlocutor,
    via,
    callType?.name
)