package io.aircall.android.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.aircall.android.R
import io.aircall.android.data.auth.AuthManager

class NavigatorImpl(private val authManager: AuthManager): Navigator {

    override fun startAuth(source: Fragment, requestCode: Int) {
        val intent = authManager.createAuthIntent()
        source.startActivityForResult(intent, requestCode)
    }

    override fun navigateUp(source: Fragment) {
        source.findNavController().navigateUp()
    }

    override fun navigateToLoginFragment(source: Fragment) {
        source.findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
    }
}