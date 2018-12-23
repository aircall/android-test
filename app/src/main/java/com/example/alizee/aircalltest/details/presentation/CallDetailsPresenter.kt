package com.example.alizee.aircalltest.details.presentation

import android.support.annotation.VisibleForTesting
import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.di.IoScheduler
import com.example.alizee.aircalltest.core.di.MainThreadScheduler
import com.example.alizee.aircalltest.details.domain.toCallDetails
import com.example.alizee.aircalltest.details.data.repository.DetailsRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

internal class CallDetailsPresenter @Inject constructor(
    private val detailsRepository: DetailsRepository,
    @MainThreadScheduler private val mainThreadScheduler: Scheduler,
    @IoScheduler private val ioScheduler: Scheduler
) {

    private var screen: CallDetailsScreen? = null
    private val compositeDisposable = CompositeDisposable()
    @VisibleForTesting
    internal lateinit var idCall: String

    fun bind(screen: CallDetailsScreen) {
        this.screen = screen
    }

    fun unbind() {
        this.screen = null
        compositeDisposable.clear()
    }

    fun start(idCall: String) {
        this.idCall = idCall

        compositeDisposable.add(
            detailsRepository.fetchCall(idCall).subscribeOn(ioScheduler).observeOn(mainThreadScheduler).subscribe({
                screen?.stopLoading()

                val callDetails = it.toCallDetails()
                screen?.displayBigPhoneNumber(callDetails.interlocutor)
                screen?.displayRecapPhoneNumber(callDetails.interlocutor)
                screen?.displayViaAndHour(R.string.via, callDetails.via, callDetails.hour)
                callDetails.direction?.let {
                    screen?.displayCallIcon(it)
                }
                callDetails.action?.let {
                    screen?.displayAction(it)
                }
            }, {
                screen?.stopLoading()
                screen?.displayError(R.string.error_details)
            })
        )
    }

    fun archiveCall() {
        compositeDisposable.add(
            detailsRepository.archiveCall(idCall).subscribeOn(ioScheduler).observeOn(
                mainThreadScheduler
            ).subscribe({
                screen?.onCallArchived(idCall)
            }, {
                screen?.displayError(R.string.error_archive)
            })
        )
    }
}