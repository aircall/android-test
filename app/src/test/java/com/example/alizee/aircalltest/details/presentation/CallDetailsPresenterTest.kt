package com.example.alizee.aircalltest.details.presentation

import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.details.data.repository.DetailsRepository
import com.example.alizee.aircalltest.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.trampoline
import org.junit.After
import org.junit.Before
import org.junit.Test

class CallDetailsPresenterTest {

    private val detailsRepository by lazy { mock<DetailsRepository>() }
    private val screen by lazy { mock<CallDetailsScreen>() }

    private lateinit var presenter: CallDetailsPresenter
    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Before
    fun setup(){
        presenter = CallDetailsPresenter(detailsRepository, trampoline(), trampoline())
        presenter.bind(screen)
    }

    @After
    fun tearDown(){
        presenter.unbind()
    }

    @Test
    fun start_fetchCalls_success(){
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
        doReturn(Observable.just(call)).`when`(detailsRepository).fetchCall("id_call")

        //When
        presenter.start("id_call")

        //Then
        verify(screen).stopLoading()
        verify(screen).displayBigPhoneNumber("0788998866")
        verify(screen).displayRecapPhoneNumber("0788998866")
        verify(screen).displayViaAndHour(R.string.via, "Paris office", "12:23")
        verify(screen).displayCallIcon(R.drawable.ic_outbound)
        verify(screen).displayAction(R.string.voicemail)
    }

    @Test
    fun start_fetchCalls_fail(){
        //Given
        doReturn(Observable.error<Throwable>(Throwable("error"))).`when`(detailsRepository).fetchCall("id_call")

        //When
        presenter.start("id_call")

        //Then
        verify(screen).stopLoading()
        verify(screen).displayError(R.string.error_details)
    }

    @Test
    fun on_archive_success(){
        //Given
        presenter.idCall = "id_call"
        doReturn(Completable.complete()).`when`(detailsRepository).archiveCall("id_call")


        //When
        presenter.archiveCall()

        //Then
        verify(screen).onCallArchived("id_call")
    }

    @Test
    fun on_archive_fail(){
        //Given
        presenter.idCall = "id_call"
        doReturn(Completable.error(Throwable("error"))).`when`(detailsRepository).archiveCall("id_call")


        //When
        presenter.archiveCall()

        //Then
        verify(screen).displayError(R.string.error_archive)
    }
}