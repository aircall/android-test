package com.example.alizee.aircalltest.feed.presentation

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.feed.domain.CallFeed

class CallViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    private val callDate by lazy { itemView.findViewById<TextView>(R.id.call_date) }
    private val toNumber by lazy { itemView.findViewById<TextView>(R.id.call_number) }
    private val fromNumber by lazy { itemView.findViewById<TextView>(R.id.via) }
    private val action by lazy { itemView.findViewById<TextView>(R.id.action) }
    private val icon by lazy { itemView.findViewById<ImageView>(R.id.icon_call) }

    fun bind(call: CallFeed) {
        callDate.text = call.date
        toNumber.text = call.interlocutor
        fromNumber.text = context.resources.getString(R.string.via, call.via, call.hour)
        call.direction?.let {
            icon.setImageDrawable(ContextCompat.getDrawable(context, it))
        }
        call.action?.let {
            action.text = context.resources.getString(it)
        }
    }
}