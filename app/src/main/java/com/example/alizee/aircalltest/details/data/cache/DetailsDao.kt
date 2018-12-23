package com.example.alizee.aircalltest.details.data.cache

import android.arch.persistence.room.*
import com.example.alizee.aircalltest.core.data.cache.CallCache
import io.reactivex.Maybe


@Dao
interface DetailsDao {

    @Query("SELECT * FROM call_table WHERE id = :id LIMIT 1")
    fun findById(id: String): Maybe<CallCache>

    @Query("DELETE FROM call_table WHERE id = :id")
    fun delete(id: String)
}