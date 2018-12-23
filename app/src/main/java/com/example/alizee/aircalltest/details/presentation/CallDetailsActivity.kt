package com.example.alizee.aircalltest.details.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View.GONE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.alizee.aircalltest.R
import com.example.alizee.aircalltest.core.AircallTestApplication
import com.example.alizee.aircalltest.core.EXTRA_CALL_ID
import javax.inject.Inject

class CallDetailsActivity : AppCompatActivity(), CallDetailsScreen {
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val phoneNumberBigView by lazy { findViewById<TextView>(R.id.phone_number_big) }
    private val phoneNumberRecapView by lazy { findViewById<TextView>(R.id.phone_number_recap) }
    private val iconCallView by lazy { findViewById<ImageView>(R.id.icon_call) }
    private val actionView by lazy { findViewById<TextView>(R.id.action) }
    private val viaView by lazy { findViewById<TextView>(R.id.via) }
    private val archiveView by lazy { findViewById<ConstraintLayout>(R.id.layout_archive) }

    private val loaderTitle by lazy { findViewById<ProgressBar>(R.id.loader_title) }
    private val loaderRecap by lazy { findViewById<ProgressBar>(R.id.loader_recap) }

    private val callId by lazy { intent.getStringExtra(EXTRA_CALL_ID) }

    @Inject
    internal lateinit var presenter: CallDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)

        AircallTestApplication.instance.getComponent().inject(this)

        presenter.bind(this)
        presenter.start(callId)

        archiveView.setOnClickListener { presenter.archiveCall() }

    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun displayRecapPhoneNumber(phoneNumber: String) {
        phoneNumberRecapView.text = phoneNumber
    }

    override fun displayAction(@StringRes action: Int) {
        actionView.text = resources.getString(action)
    }

    override fun displayViaAndHour(@StringRes translationRes: Int, via: String, hour: String) {
        viaView.text = resources.getString(translationRes, via, hour)
    }

    override fun displayCallIcon(icon: Int) {
        iconCallView.setImageDrawable(ContextCompat.getDrawable(this, icon))
    }

    override fun displayBigPhoneNumber(phoneNumber: String) {
        phoneNumberBigView.text = phoneNumber
    }

    override fun onCallArchived(id: String) {
        val intent = Intent()
        intent.putExtra(EXTRA_CALL_ID, id)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun stopLoading() {
        loaderTitle.visibility = GONE
        loaderRecap.visibility = GONE
    }

    override fun displayError(@StringRes errorMessage: Int) {
        Toast.makeText(this, resources.getString(errorMessage), Toast.LENGTH_LONG).show()
    }
}