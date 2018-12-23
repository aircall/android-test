package com.example.alizee.aircalltest.details.data.repository

import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.core.data.domain.toDomain
import com.example.alizee.aircalltest.core.data.network.CallNetworkTransformer
import com.example.alizee.aircalltest.details.data.cache.DetailsDao
import com.example.alizee.aircalltest.details.data.network.ArchiveCallBody
import com.example.alizee.aircalltest.details.data.network.DetailsApi
import io.reactivex.Completable
import io.reactivex.Observable

class DetailsRepository constructor(
    private val detailsApi: DetailsApi,
    private val dao: DetailsDao,
    private val transformer: CallNetworkTransformer
) {

    /**
     * Fetch a specific call from the API
     * If there is an error, get this call from the cache
     *
     *  @param id Id of the call
     *  @return The Observable of the Call
     */
    fun fetchCall(id: String): Observable<Call> {
        return getCallFromNetwork(id).onErrorResumeNext(getCallFromCache(id))
    }

    /**
     * Archive a call
     *
     * @param id Id of the call
     * @return Completable
     */
    fun archiveCall(id: String): Completable {
        return detailsApi.archiveCall(id, ArchiveCallBody()).doOnComplete { dao.delete(id) }
    }

    private fun getCallFromCache(id: String): Observable<Call> {
        return dao.findById(id).map { cachedCall ->
            cachedCall.toDomain()
        }.toObservable()
    }

    private fun getCallFromNetwork(id: String): Observable<Call> {
        return detailsApi.getCall(id).map { call ->
            transformer.transformCallNetworkToCallDomain(call)
        }
    }

}