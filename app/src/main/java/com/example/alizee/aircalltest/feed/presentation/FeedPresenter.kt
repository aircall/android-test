package com.example.alizee.aircalltest.feed.presentation

import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.di.IoScheduler
import com.example.alizee.aircalltest.core.di.MainThreadScheduler
import com.example.alizee.aircalltest.feed.data.repository.FeedRepository
import com.example.alizee.aircalltest.feed.domain.toCallFeed
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

internal class FeedPresenter @Inject constructor(
    @MainThreadScheduler private val mainThreadScheduler: Scheduler,
    @IoScheduler private val ioScheduler: Scheduler,
    private val feedRepository: FeedRepository
) {

    private var screen: FeedScreen? = null
    private val compositeDisposable = CompositeDisposable()

    fun bind(screen: FeedScreen) {
        this.screen = screen
    }

    fun unbind() {
        this.screen = null
        this.compositeDisposable.clear()
    }

    fun start() {
        compositeDisposable.add(
            feedRepository.fetchAllActiveCalls().subscribeOn(ioScheduler).observeOn(mainThreadScheduler).subscribe(
                { calls ->
                    screen?.stopLoading()
                    if (calls.isEmpty()){
                        screen?.displayEmptyState()
                    } else {
                        val callFeeds = calls.map { it.toCallFeed() }
                        screen?.displayCalls(callFeeds)
                    }
                },
                {
                    screen?.stopLoading()
                    screen?.displayError(R.string.error_feed)
                })
        )
    }
}