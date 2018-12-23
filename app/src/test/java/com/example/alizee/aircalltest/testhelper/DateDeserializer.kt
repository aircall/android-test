package com.example.alizee.aircalltest.testhelper

import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateDeserializer : JsonDeserializer<Date> {

    @Throws(JsonParseException::class)
    override fun deserialize(element: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Date? {
        if (element.isJsonPrimitive) {
            val primitiveElement = element as JsonPrimitive

            // handle TTL in seconds
            if (primitiveElement.isNumber) {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.SECOND, element.getAsInt())
                return calendar.time
            }

            // handle formatted date
            if (primitiveElement.isString) {
                return parseDate(element.getAsString(), DATE_FORMAT)
            }
        }

        return null
    }

    private fun parseDate(date: String, format: String): Date? {
        val formatter = SimpleDateFormat(format, Locale.getDefault())

        try {
            return formatter.parse(date)
        } catch (e: ParseException) {
            return null
        }

    }

    companion object {
        val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    }
}