package com.example.alizee.aircalltest.core.utils

import java.util.*

interface DateHelper {
    fun formatApiDateToSummaryDate(date: Date): String
    fun formatApiDateToTime(date: Date): String
}