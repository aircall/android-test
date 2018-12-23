package com.example.alizee.aircalltest.feed.presentation

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.AircallTestApplication
import com.example.alizee.aircalltest.core.EXTRA_CALL_ID
import com.example.alizee.aircalltest.details.presentation.CallDetailsActivity
import com.example.alizee.aircalltest.feed.domain.CallFeed
import javax.inject.Inject

class FeedActivity : AppCompatActivity(), FeedScreen {

    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val feedListView by lazy { findViewById<RecyclerView>(R.id.feed_list) }
    private val loaderView by lazy { findViewById<ProgressBar>(R.id.loader_feed) }
    private val emptyState by lazy { findViewById<TextView>(R.id.empty_state) }

    @Inject
    internal lateinit var presenter: FeedPresenter

    private lateinit var callList: List<CallFeed>
    private lateinit var adapter: FeedListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_feed)
        toolbar.title = resources.getString(R.string.activity_feed)
        setSupportActionBar(toolbar)

        AircallTestApplication.instance.getComponent().inject(this)

        presenter.bind(this)
        presenter.start()
    }

    override fun displayCalls(calls: List<CallFeed>) {
        this.callList = calls
        val viewManager = LinearLayoutManager(this)
        adapter = FeedListAdapter(calls, this, ::onCallClicked)
        feedListView.layoutManager = viewManager
        feedListView.adapter = adapter
    }

    override fun stopLoading() {
        loaderView.visibility = GONE
    }

    override fun displayError(@StringRes errorMessage: Int) {
        Toast.makeText(this, resources.getString(errorMessage), Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyState() {
        emptyState.visibility = VISIBLE
    }

    private fun onCallClicked(id: String) {
        val intent = Intent(this, CallDetailsActivity::class.java)
        intent.putExtra(EXTRA_CALL_ID, id)
        startActivityForResult(intent, resources.getInteger(R.integer.req_details))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == resources.getInteger(R.integer.req_details) && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(EXTRA_CALL_ID)?.let {
                callList = callList.filter { it.id != data.getStringExtra(EXTRA_CALL_ID) }
                adapter.resetList(callList)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }
}
