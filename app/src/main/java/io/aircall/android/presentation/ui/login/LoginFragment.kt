package io.aircall.android.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.aircall.android.OpenForTesting
import io.aircall.android.R
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.databinding.LoginFragmentBinding
import io.aircall.android.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.top_kotlin_public_repositories_fragment.*
import javax.inject.Inject

@OpenForTesting
class LoginFragment : DaggerFragment() {

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var navigator: Navigator

    private val snackbar: Snackbar by lazy {
        Snackbar.make(main, R.string.unknown_error, Snackbar.LENGTH_SHORT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loginButton.setOnClickListener {
            navigator.startAuth(this, RC_AUTH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_AUTH -> {
                data?.run {
                    authManager.processAuthResult(data,
                        {
                            navigator.navigateUp(this@LoginFragment)
                        },
                        { error ->
                            snackbar.setText(error.message.toString())
                            snackbar.show()
                        }
                    )
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val RC_AUTH = 1
    }
}