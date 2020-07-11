package io.aircall.android.domain.date

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {
    private val parser: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    private val formatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    fun getCalendar(): Calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"))
    fun String.toDate(): Date? = parser.parse(this)
    fun Date.toDateString(): String = formatter.format(this)
}