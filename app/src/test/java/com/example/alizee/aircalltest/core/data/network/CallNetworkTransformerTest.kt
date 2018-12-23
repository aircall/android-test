package com.example.alizee.aircalltest.core.data.network

import com.example.alizee.aircalltest.core.data.Action
import com.example.alizee.aircalltest.core.data.Direction
import com.example.alizee.aircalltest.core.utils.DateHelper
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CallNetworkTransformerTest {

    private val datesHelper by lazy { mock<DateHelper>() }
    val gson: Gson = GsonBuilder.getDefaultBuilder().create()
    lateinit var transformer: CallNetworkTransformer

    @Before
    fun setup() {
        transformer = CallNetworkTransformer(datesHelper)
    }


    @Test
    fun `callNetwork_shouldBeConvertedTo_call`() {
        //Given
        val callNetwork = gson.fromJson<CallNetwork>(
            """
             {
                 "id": "id_call",
                 "created_at": "2018-04-18T16:42:55.000Z",
                 "direction": "outbound",
                 "from": "0788998866",
                 "to": "0655446765",
                 "via": "Paris office",
                 "duration": "2000",
                 "is_archived": false,
                 "call_type": "voicemail"
             }
         """.trimIndent()
            , CallNetwork::class.java
        )
        doReturn("18 Avril").`when`(datesHelper)
            .formatApiDateToSummaryDate(any())
        doReturn("16:42").`when`(datesHelper)
            .formatApiDateToTime(any())

        //When
        val call = transformer.transformCallNetworkToCallDomain(callNetwork)

        //Then
        assertEquals("id_call", call.id)
        assertEquals("18 Avril", call.date)
        assertEquals("16:42", call.hour)
        assertEquals(Direction.OUTBOUND, call.direction)
        assertEquals("0655446765", call.interlocutor)
        assertEquals("Paris office", call.via)
        assertEquals(Action.VOICEMAIL, call.callType)
    }


}