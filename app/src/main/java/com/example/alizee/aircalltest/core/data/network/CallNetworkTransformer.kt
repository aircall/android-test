package com.example.alizee.aircalltest.core.data.network

import com.example.alizee.aircalltest.core.utils.DateHelper
import com.example.alizee.aircalltest.core.data.domain.Call
import com.example.alizee.aircalltest.core.data.Direction
import javax.inject.Inject

class CallNetworkTransformer @Inject constructor(private val dateHelper: DateHelper) {

    fun transformCallNetworkToCallDomain(callNetwork: CallNetwork): Call {
        val date = dateHelper.formatApiDateToSummaryDate(callNetwork.createdAt)
        val hour = dateHelper.formatApiDateToTime(callNetwork.createdAt)

        val interlocutor = if (callNetwork.direction == Direction.OUTBOUND) callNetwork.to!! else callNetwork.from

        return Call(
            callNetwork.id,
            date,
            hour,
            callNetwork.direction,
            interlocutor,
            callNetwork.via,
            callNetwork.callType
        )
    }
}