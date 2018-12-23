package com.example.alizee.aircalltest.core.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.example.alizee.aircalltest.core.utils.AppDateHelper
import com.example.alizee.aircalltest.core.BASE_URL
import com.example.alizee.aircalltest.core.utils.DateHelper
import com.example.alizee.aircalltest.core.data.cache.AppDatabase
import com.example.alizee.aircalltest.core.data.network.CallNetworkTransformer
import com.example.alizee.aircalltest.details.data.network.DetailsApi
import com.example.alizee.aircalltest.details.data.repository.DetailsRepository
import com.example.alizee.aircalltest.feed.data.network.FeedApi
import com.example.alizee.aircalltest.feed.data.repository.FeedRepository
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @ApplicationContext
    @Provides
    @Singleton
    fun provideApplicationContext() = application.applicationContext

    @MainThreadScheduler
    @Provides
    @Singleton
    internal fun provideMainThreadScheduler(): Scheduler {
        return mainThread()
    }

    @IoScheduler
    @Provides
    @Singleton
    internal fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addNetworkInterceptor(StethoInterceptor())
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Provides
    @Singleton
    fun provideFeedApi(retrofit: Retrofit): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDetailsApi(retrofit: Retrofit): DetailsApi {
        return retrofit.create(DetailsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(
        database: AppDatabase,
        detailsApi: DetailsApi,
        transformer: CallNetworkTransformer
    ): DetailsRepository {
        return DetailsRepository(detailsApi, database.detailsDao(), transformer)
    }

    @Provides
    @Singleton
    fun provideFeedRepository(
        database: AppDatabase,
        feedApi: FeedApi,
        transformer: CallNetworkTransformer
    ): FeedRepository {
        return FeedRepository(feedApi, database.feedDao(), transformer)
    }

    @Provides
    @Singleton
    fun provideDateHelper(): DateHelper = AppDateHelper()

}