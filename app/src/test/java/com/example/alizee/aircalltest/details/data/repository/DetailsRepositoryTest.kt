package com.example.alizee.aircalltest.details.data.repository

import com.example.alizee.aircalltest.core.data.cache.CallCache
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.core.data.network.CallNetwork
import com.example.alizee.aircalltest.core.data.network.CallNetworkTransformer
import com.example.alizee.aircalltest.details.data.cache.DetailsDao
import com.example.alizee.aircalltest.details.data.network.DetailsApi
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DetailsRepositoryTest {

    private val detailsApi by lazy { mock<DetailsApi>() }
    private val dao by lazy { mock<DetailsDao>() }
    private val transformer by lazy { mock<CallNetworkTransformer>() }
    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    private lateinit var repository: DetailsRepository

    val callNetwork = gson.fromJson<CallNetwork>(
        """
             {
                 "id": "id_call_from_network",
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

    val callCache = gson.fromJson<CallCache>(
        """
             {
                 "id": "id_call_from_cache",
                 "date": "2 Nov",
                 "hour": "12:23",
                 "direction": "OUTBOUND",
                 "interlocutor_number": "0788998866",
                 "via": "Paris office",
                 "call_type": "VOICEMAIL"
             }
         """.trimIndent()
        , CallCache::class.java
    )

    @Before
    fun setup() {
        repository = DetailsRepository(detailsApi, dao, transformer)
    }

    @Test
    fun fetchcall_networkSuccess() {
        //Given
        val call = gson.fromJson<Call>(
            """
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
            , Call::class.java
        )
        doReturn(Observable.just(callNetwork)).`when`(detailsApi).getCall("id_call")
        doReturn(call).`when`(transformer).transformCallNetworkToCallDomain(callNetwork)
        doReturn(Maybe.just(callCache)).`when`(dao).findById("id_call")

        //When
        val result = repository.fetchCall("id_call")

        //Then
        val testObserver = TestObserver<Call>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val callResult = testObserver.values()[0]
        Assert.assertEquals(call, callResult)
    }

    @Test
    fun fetchcall_networkFailure() {
        //Given
        val call = gson.fromJson<Call>(
            """
             {
                 "id": "id_call_from_cache",
                 "date": "2 Nov",
                 "hour": "12:23",
                 "direction": "outbound",
                 "interlocutor": "0788998866",
                 "via": "Paris office",
                 "call_type": "voicemail"
             }
         """.trimIndent()
            , Call::class.java
        )
        val throwable = Throwable("Error")
        doReturn(Observable.error<Throwable>(throwable)).`when`(detailsApi).getCall("id_call")
        doReturn(Maybe.just(callCache)).`when`(dao).findById("id_call")

        //When
        val result = repository.fetchCall("id_call")

        //Then
        val testObserver = TestObserver<Call>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val callResult = testObserver.values()[0]
        Assert.assertEquals(call, callResult)
    }

    @Test
    fun on_archive() {
        //Given
        doReturn(Completable.complete()).`when`(detailsApi).archiveCall(eq("id_call"), any())

        //When
        val result = repository.archiveCall("id_call")

        //Then
        val testObserver = TestObserver<Call>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        verify(dao).delete("id_call")
    }
}