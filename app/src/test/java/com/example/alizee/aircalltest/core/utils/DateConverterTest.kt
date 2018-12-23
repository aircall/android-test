package com.example.alizee.aircalltest.core.utils

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat

class DateConverterTest {

    @Test
    fun convert_timestamp_to_date(){
        //Given
        val timestamp = 592786800000L

        //When
        val date = DateConverter().fromTimestamp(timestamp)

        //Then
        val dateString = SimpleDateFormat("yyyy-MM-dd").format(date)
        assertEquals("1988-10-14", dateString)
    }

    @Test
    fun convert_date_to_timestamp(){
        //Given
        val date  = SimpleDateFormat("yyyy-MM-dd").parse("1988-10-14")

        //When
        val timestamp = DateConverter().fromDate(date)

        //Then
        assertEquals( 592786800000L, timestamp)
    }
}