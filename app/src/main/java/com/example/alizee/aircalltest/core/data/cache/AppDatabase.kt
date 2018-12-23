package com.example.alizee.aircalltest.core.data.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.alizee.aircalltest.core.utils.DateConverter
import com.example.alizee.aircalltest.details.data.cache.DetailsDao
import com.example.alizee.aircalltest.feed.data.cache.FeedDao

@TypeConverters(DateConverter::class)
@Database(entities = [CallCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedDao(): FeedDao

    abstract fun detailsDao(): DetailsDao

}