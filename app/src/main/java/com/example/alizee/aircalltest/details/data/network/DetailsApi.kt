package com.example.alizee.aircalltest.details.data.network

import com.example.alizee.aircalltest.core.data.network.CallNetwork
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetailsApi {

    @GET("/activities/{id}")
    fun getCall(@Path("id") id: String): Observable<CallNetwork>

    @POST("/activities/{id}")
    fun archiveCall(@Path("id") id: String, @Body body: ArchiveCallBody): Completable
}