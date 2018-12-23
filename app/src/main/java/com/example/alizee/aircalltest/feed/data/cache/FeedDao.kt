package com.example.alizee.aircalltest.feed.data.cache

import android.arch.persistence.room.*
import com.example.alizee.aircalltest.core.data.cache.CallCache
import io.reactivex.Maybe

@Dao
interface FeedDao {
    @Query("SELECT * FROM call_table")
    fun getAll(): Maybe<List<CallCache>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(call: CallCache)
}