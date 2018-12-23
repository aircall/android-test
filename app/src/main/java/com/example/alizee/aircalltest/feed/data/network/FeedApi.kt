package com.example.alizee.aircalltest.feed.data.network

import com.example.alizee.aircalltest.core.data.network.CallNetwork
import io.reactivex.Observable
import retrofit2.http.GET

interface FeedApi {

    @GET("/activities")
    fun getCalls(): Observable<List<CallNetwork>>
}