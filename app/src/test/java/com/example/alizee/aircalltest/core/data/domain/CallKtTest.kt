package com.example.alizee.aircalltest.core.data.domain

import com.example.alizee.aircalltest.core.data.Action
import com.example.alizee.aircalltest.core.data.Direction
import com.example.alizee.aircalltest.core.data.cache.CallCache
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class CallKtTest {

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Test
    fun `callCache_shouldBeConvertedTo_call`() {
        //Given
        val callCache = gson.fromJson<CallCache>("""
             {
                 "id": "id_call",
                 "date": "2 Nov",
                 "hour": "12:23",
                 "direction": "OUTBOUND",
                 "interlocutor_number": "0788998866",
                 "via": "Paris office",
                 "call_type": "VOICEMAIL"
             }
         """.trimIndent()
            , CallCache::class.java)


        //When
        val call = callCache.toDomain()

        //Then
        assertEquals("id_call", call.id)
        assertEquals("2 Nov", call.date)
        assertEquals("12:23", call.hour)
        assertEquals(Direction.OUTBOUND, call.direction)
        assertEquals("0788998866", call.interlocutor)
        assertEquals("Paris office", call.via)
        assertEquals(Action.VOICEMAIL, call.callType)
    }
}