package com.example.alizee.aircalltest.details.domain

import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class CallDetailsKtTest {

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Test
    fun convert_call_to_calldetails(){
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
        val callDetails = call.toCallDetails()

        //Then
        assertEquals("id_call", callDetails.id)
        assertEquals("12:23", callDetails.hour)
        assertEquals(R.drawable.ic_outbound, callDetails.direction)
        assertEquals("0788998866", callDetails.interlocutor)
        assertEquals("Paris office", callDetails.via)
        assertEquals(R.string.voicemail, callDetails.action)
    }
}