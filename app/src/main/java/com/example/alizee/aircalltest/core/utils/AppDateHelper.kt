package com.example.alizee.aircalltest.core.utils

import java.text.DateFormat
import java.util.*

class AppDateHelper: DateHelper {

    /**
     * Format Date to a string with the full date, depending on the locale
     * of the user
     *
     * ex : input Date()
     * output : 19 avr. 2018
     */
    override fun formatApiDateToSummaryDate(date: Date): String {
        return DateFormat.getDateInstance().format(date)
    }

    /**
     * Format Date to a string with the time
     *
     * ex : input Date()
     * output : 12:34
     */
    override fun formatApiDateToTime(date: Date): String {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date)
    }
}