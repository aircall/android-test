package com.example.alizee.aircalltest.feed.data.repository

import com.example.alizee.aircalltest.core.data.cache.toCache
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.core.data.domain.toDomain
import com.example.alizee.aircalltest.core.data.network.CallNetworkTransformer
import com.example.alizee.aircalltest.feed.data.cache.FeedDao
import com.example.alizee.aircalltest.feed.data.network.FeedApi
import io.reactivex.Maybe
import io.reactivex.Observable

class FeedRepository constructor(
    private val feedApi: FeedApi,
    private val dao: FeedDao,
    private val transformer: CallNetworkTransformer
) {

    /**
     * Fetch all calls that are not archived from the API
     * and put them in cache.
     * If there is an error, get calls from the cache
     *
     */
    fun fetchAllActiveCalls(): Observable<List<Call>> {
        return getCallsFromNetwork().onErrorResumeNext(getCallsFromCache())
    }

    private fun getCallsFromCache(): Observable<List<Call>> {
        return dao.getAll().flatMap { cachedList ->
            if (cachedList.isEmpty()) {
                Maybe.error(Error("No data available"))
            } else {
                Maybe.just(cachedList.map { it.toDomain() })
            }
        }.toObservable()
    }

    private fun getCallsFromNetwork(): Observable<List<Call>> {
        return feedApi.getCalls().map { list ->
            list.filter { !it.isArchived }.map { transformer.transformCallNetworkToCallDomain(it) }
        }.doOnNext { storeCallsInCache(it) }
    }

    private fun storeCallsInCache(calls: List<Call>) {
        for (call in calls) {
            dao.insert(call.toCache())
        }
    }
}