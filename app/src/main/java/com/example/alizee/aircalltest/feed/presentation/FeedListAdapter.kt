package com.example.alizee.aircalltest.feed.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.feed.domain.CallFeed

class FeedListAdapter(
    private var callList: List<CallFeed>,
    private val context: Context,
    private val onClickAction: (id: String) -> Unit
) : RecyclerView.Adapter<CallViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.call_item, parent, false)
        return CallViewHolder(textView, context)
    }

    override fun getItemCount() = callList.size

    override fun onBindViewHolder(viewHolder: CallViewHolder, position: Int) {
        val call = callList[position]
        viewHolder.bind(call)
        viewHolder.itemView.setOnClickListener { onClickAction(call.id) }
    }

    fun resetList(calls: List<CallFeed>) {
        callList = calls
    }

}