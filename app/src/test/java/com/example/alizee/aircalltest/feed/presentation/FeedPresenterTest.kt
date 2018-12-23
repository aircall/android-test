package com.example.alizee.aircalltest.feed.presentation

import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.feed.data.repository.FeedRepository
import com.example.alizee.aircalltest.feed.domain.CallFeed
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FeedPresenterTest {
    private val feedRepository by lazy { mock<FeedRepository>() }
    private val screen by lazy { mock<FeedScreen>() }

    private lateinit var presenter: FeedPresenter
    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Before
    fun setup() {
        presenter = FeedPresenter(Schedulers.trampoline(), Schedulers.trampoline(), feedRepository)
        presenter.bind(screen)
    }

    @After
    fun tearDown() {
        presenter.unbind()
    }

    @Test
    fun start_fetch_success(){
        //Given
        val call = gson.fromJson<Call>("""
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
            , Call::class.java)
        doReturn(Observable.just(listOf(call))).`when`(feedRepository).fetchAllActiveCalls()
        val captureCallFeed = argumentCaptor<List<CallFeed>>()

        //When
        presenter.start()

        //Then
        verify(screen).stopLoading()
        verify(screen).displayCalls(captureCallFeed.capture())
        val callFeed = captureCallFeed.firstValue[0]
        assertEquals("id_call", callFeed.id)
        assertEquals("2 Nov", callFeed.date)
        assertEquals("12:23", callFeed.hour)
        assertEquals(R.drawable.ic_outbound, callFeed.direction)
        assertEquals("0788998866", callFeed.interlocutor)
        assertEquals("Paris office", callFeed.via)
        assertEquals(R.string.voicemail, callFeed.action)
    }

    @Test
    fun start_fetch_success_empty(){
        //Given
        doReturn(Observable.just(listOf<Call>())).`when`(feedRepository).fetchAllActiveCalls()

        //When
        presenter.start()

        //Then
        verify(screen).stopLoading()
        verify(screen).displayEmptyState()
    }

    @Test
    fun start_fetch_fail() {
        //Given
        doReturn(Observable.error<Throwable>(Throwable("error"))).`when`(feedRepository).fetchAllActiveCalls()

        //When
        presenter.start()

        //Then
        verify(screen).stopLoading()
        verify(screen).displayError(R.string.error_feed)
    }
}