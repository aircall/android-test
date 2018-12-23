package com.example.alizee.aircalltest.feed.data.repository

import com.example.alizee.aircalltest.core.data.cache.CallCache
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.core.data.network.CallNetwork
import com.example.alizee.aircalltest.core.data.network.CallNetworkTransformer
import com.example.alizee.aircalltest.feed.data.cache.FeedDao
import com.example.alizee.aircalltest.feed.data.network.FeedApi
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FeedRepositoryTest{
    private val feedApi by lazy { mock<FeedApi>() }
    private val dao by lazy { mock<FeedDao>() }
    private val transformer by lazy { mock<CallNetworkTransformer>() }
    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    private lateinit var repository: FeedRepository

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
    val callNetworkArchived = gson.fromJson<CallNetwork>(
        """
             {
                 "id": "id_call_from_network",
                 "created_at": "2018-04-18T16:42:55.000Z",
                 "direction": "outbound",
                 "from": "0788998866",
                 "to": "0655446765",
                 "via": "Paris office",
                 "duration": "2000",
                 "is_archived": true,
                 "call_type": "voicemail"
             }
         """.trimIndent()
        , CallNetwork::class.java
    )

    val callCache = gson.fromJson<CallCache>(
        """
             {
                 "id": "id_call",
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
        repository = FeedRepository(feedApi, dao, transformer)
    }

    @Test
    fun fetchcalls_networkSuccess() {
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
        doReturn(Observable.just(listOf(callNetwork, callNetworkArchived))).`when`(feedApi).getCalls()
        doReturn(call).`when`(transformer).transformCallNetworkToCallDomain(callNetwork)
        doReturn(Maybe.just(listOf(callCache))).`when`(dao).getAll()

        //When
        val result = repository.fetchAllActiveCalls()

        //Then
        val testObserver = TestObserver<List<Call>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val callResult = testObserver.values()[0]
        Assert.assertEquals(call, callResult[0])
        verify(dao).insert(callCache)
    }

    @Test
    fun fetchcall_networkFailure() {
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
        val throwable = Throwable("Error")
        doReturn(Observable.error<Throwable>(throwable)).`when`(feedApi).getCalls()
        doReturn(Maybe.just(listOf(callCache))).`when`(dao).getAll()

        //When
        val result = repository.fetchAllActiveCalls()

        //Then
        val testObserver = TestObserver<List<Call>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val callResult = testObserver.values()[0]
        Assert.assertEquals(call, callResult[0])
    }

    @Test
    fun fetchcall_networkFailure_cacheEmpty() {
        //Given
        val throwable = Throwable("Error")
        doReturn(Observable.error<Throwable>(throwable)).`when`(feedApi).getCalls()
        doReturn(Maybe.just(listOf<CallCache>())).`when`(dao).getAll()

        //When
        val result = repository.fetchAllActiveCalls()

        //Then
        val testObserver = TestObserver<List<Call>>()
        result.subscribe(testObserver)
        testObserver.assertError(Error::class.java)
        testObserver.assertNotComplete()
    }
}