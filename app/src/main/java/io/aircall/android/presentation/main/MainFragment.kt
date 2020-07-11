package io.aircall.android.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.aircall.android.OpenForTesting
import io.aircall.android.R
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.databinding.MainFragmentBinding
import io.aircall.android.domain.exception.UserNotAuthenticated
import io.aircall.android.presentation.navigation.IntentLauncher
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@OpenForTesting
class MainFragment : DaggerFragment() {
    @Inject
    lateinit var intentLauncher: IntentLauncher

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val snackbar: Snackbar by lazy {
        Snackbar.make(main, R.string.unknown_error, Snackbar.LENGTH_SHORT)
    }

    private lateinit var mainFragmentBinding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        mainFragmentBinding.mainViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainFragmentBinding.lifecycleOwner = viewLifecycleOwner
        return mainFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        viewModel.launchGetUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_AUTH -> {
                data?.run {
                    authManager.processAuthResult(data) {
                        viewModel.launchGetUser()
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initUi() {
        loginButton.setOnClickListener {
            intentLauncher.startAuth(this, RC_AUTH)
        }
        observeError()
    }

    private fun observeError() {
        viewModel.errorLiveData.observe(
            viewLifecycleOwner,
            Observer<Throwable> { throwable ->
                val message = when (throwable) {
                    is UserNotAuthenticated -> getString(R.string.user_unlogged_message)
                    else -> throwable.message.toString()
                }
                snackbar.setText(message)
                snackbar.show()
            })
    }

    companion object {
        private const val RC_AUTH = 1
    }
}