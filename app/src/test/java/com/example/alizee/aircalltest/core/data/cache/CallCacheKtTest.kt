package com.example.alizee.aircalltest.core.data.cache

import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class CallCacheKtTest {

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Test
    fun `call_shouldBeConvertedTo_cacheCall`() {
        //Given
        val call = gson.fromJson<Call>("""
             {
                 "id": "id_call",
                 "date": "2 Nov",
                 "hour": "12:23",
                 "direction": "outbound",
                 "interlocutor": "0788998866",
                 "via": "Paris office",
                 "call_type": "voicemail"
             }
         """.trimIndent()
            , Call::class.java)


        //When
        val callCache = call.toCache()

        //Then
        assertEquals("id_call", callCache.id)
        assertEquals("2 Nov", callCache.date)
        assertEquals("12:23", callCache.hour)
        assertEquals("OUTBOUND", callCache.direction)
        assertEquals("0788998866", callCache.interlocutorNumber)
        assertEquals("Paris office", callCache.via)
        assertEquals("VOICEMAIL", callCache.callType)
    }
}