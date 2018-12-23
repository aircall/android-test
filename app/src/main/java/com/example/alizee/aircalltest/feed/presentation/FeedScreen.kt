package com.example.alizee.aircalltest.feed.presentation

import android.support.annotation.StringRes
import com.example.alizee.aircalltest.feed.domain.CallFeed

interface FeedScreen {
    fun displayCalls(calls: List<CallFeed>)

    fun stopLoading()

    fun displayError(@StringRes errorMessage: Int)

    fun displayEmptyState()
}