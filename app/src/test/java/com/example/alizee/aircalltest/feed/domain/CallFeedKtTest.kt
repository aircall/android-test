package com.example.alizee.aircalltest.feed.domain

import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class CallFeedKtTest {

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Test
    fun convert_call_to_callfeed(){
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
        val callFeed = call.toCallFeed()

        //Then
        assertEquals("id_call", callFeed.id)
        assertEquals("2 Nov", callFeed.date)
        assertEquals("12:23", callFeed.hour)
        assertEquals(R.drawable.ic_outbound, callFeed.direction)
        assertEquals("0788998866", callFeed.interlocutor)
        assertEquals("Paris office", callFeed.via)
        assertEquals(R.string.voicemail, callFeed.action)
    }
}