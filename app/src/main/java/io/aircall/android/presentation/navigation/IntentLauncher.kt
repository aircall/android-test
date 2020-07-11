package io.aircall.android.presentation.navigation

import androidx.fragment.app.Fragment

interface IntentLauncher {
    fun startAuth(fragment: Fragment, requestCode: Int)
}