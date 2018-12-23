package com.example.alizee.aircalltest.core.di

import com.example.alizee.aircalltest.details.presentation.CallDetailsActivity
import com.example.alizee.aircalltest.feed.presentation.FeedActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: FeedActivity)

    fun inject(activity: CallDetailsActivity)
}