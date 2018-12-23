package com.example.alizee.aircalltest.testhelper

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.util.*

object GsonBuilder {

    private const val DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"

    @JvmStatic
    fun getDefaultBuilder(): GsonBuilder {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .setDateFormat(DATE_FORMAT)
    }
}