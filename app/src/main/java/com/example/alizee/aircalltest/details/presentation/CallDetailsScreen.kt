package com.example.alizee.aircalltest.details.presentation

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

interface CallDetailsScreen {

    fun displayBigPhoneNumber(phoneNumber: String)

    fun displayRecapPhoneNumber(phoneNumber: String)

    fun displayAction(@StringRes action: Int)

    fun displayViaAndHour(@StringRes translationRes: Int, via: String, hour: String)

    fun displayCallIcon(@DrawableRes icon: Int)

    fun onCallArchived(id: String)

    fun stopLoading()

    fun displayError(@StringRes errorMessage: Int)
}