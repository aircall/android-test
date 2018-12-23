package com.example.alizee.aircalltest.core.data.cache

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.example.alizee.aircalltest.details.data.cache.DetailsDao
import com.example.alizee.aircalltest.feed.data.cache.FeedDao
import org.junit.Before
import org.junit.runner.RunWith
import io.reactivex.observers.TestObserver
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Assert
import org.junit.Test
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var feedDao: FeedDao
    private lateinit var detailsDao: DetailsDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        feedDao = db.feedDao()
        detailsDao = db.detailsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCallAndReadInFeed() {
        val callCache = CallCache("id_call", "2 Nov", "12:34", "OUTBOUND",
            "0788998866", "Paris office", "VOICEMAIL")

        feedDao.insert(callCache)

        val result = feedDao.getAll()
        val testObserver = TestObserver<List<CallCache>>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val callResult = testObserver.values()[0]
        Assert.assertEquals(callCache, callResult[0])
    }

    @Test
    @Throws(Exception::class)
    fun writeCallAndReadDetails() {
        val callCache = CallCache("id_call", "2 Nov", "12:34", "OUTBOUND",
            "0788998866", "Paris office", "VOICEMAIL")

        feedDao.insert(callCache)

        val result = detailsDao.findById("id_call")
        val testObserver = TestObserver<CallCache>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val callResult = testObserver.values()[0]
        Assert.assertEquals(callCache, callResult)
    }

    @Test
    @Throws(Exception::class)
    fun writeCallAndRemove() {
        val callCache = CallCache("id_call", "2 Nov", "12:34", "OUTBOUND",
            "0788998866", "Paris office", "VOICEMAIL")

        feedDao.insert(callCache)

        detailsDao.delete("id_call")

        val resultSearch = detailsDao.findById("id_call")
        val testObserver = TestObserver<CallCache>()
        resultSearch.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        assertEquals(0, testObserver.valueCount())
    }
}