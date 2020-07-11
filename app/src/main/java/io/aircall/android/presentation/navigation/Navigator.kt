package io.aircall.android.presentation.navigation

import androidx.fragment.app.Fragment

interface Navigator {
    fun startAuth(source: Fragment, requestCode: Int)
    fun navigateUp(source: Fragment)
    fun navigateToLoginFragment(source: Fragment)
}