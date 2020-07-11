package io.aircall.android.presentation.navigation

import androidx.fragment.app.Fragment
import io.aircall.android.data.auth.AuthManager

class IntentLauncherImpl(private val authManager: AuthManager): IntentLauncher {
    override fun startAuth(fragment: Fragment, requestCode: Int) {
        val intent = authManager.createAuthIntent()
        fragment.startActivityForResult(intent, requestCode)
    }
}