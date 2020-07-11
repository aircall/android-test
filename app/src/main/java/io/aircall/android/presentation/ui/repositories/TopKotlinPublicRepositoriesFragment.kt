package io.aircall.android.presentation.ui.repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import io.aircall.android.OpenForTesting
import io.aircall.android.R
import io.aircall.android.data.auth.AuthManager
import io.aircall.android.databinding.TopKotlinPublicRepositoriesFragmentBinding
import io.aircall.android.domain.exception.UserNotAuthenticated
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.top_kotlin_public_repositories_fragment.*
import javax.inject.Inject

@OpenForTesting
class TopKotlinPublicRepositoriesFragment : DaggerFragment() {

    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TopKotlinPublicRepositoriesViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(TopKotlinPublicRepositoriesViewModel::class.java)
    }

    private val adapter: TopKotlinPublicRepositoriesAdapter by lazy {
        TopKotlinPublicRepositoriesAdapter(object : TopKotlinPublicRepositoriesCallback {
            override fun onKotlinPublicRepositorySelected(kotlinPublicRepository: KotlinPublicRepository) {
                val action = TopKotlinPublicRepositoriesFragmentDirections.actionMainFragmentToDetailFragment(kotlinPublicRepository)
                findNavController().navigate(action)
            }
        })
    }

    private val snackbar: Snackbar by lazy {
        Snackbar.make(main, R.string.unknown_error, Snackbar.LENGTH_SHORT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TopKotlinPublicRepositoriesFragmentBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.top_kotlin_public_repositories_fragment,
                container,
                false
            )
        binding.topKotlinPublicRepositoriesViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(TopKotlinPublicRepositoriesViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUi()
        viewModel.launchGetUser()
    }

    private fun initUi() {
        topKotlinPublicRepositoriesRecyclerView.adapter = adapter
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.topKotlinPublicRepositoriesLiveData.observe(
            viewLifecycleOwner,
            Observer { topKotlinPublicRepositories ->
                adapter.addData(topKotlinPublicRepositories)
            })
        viewModel.dataLoadingLiveData.observe(
            viewLifecycleOwner,
            Observer { dataLoading ->
                if (dataLoading) {
                    loadingLayout.startShimmer()
                } else {
                    loadingLayout.stopShimmer()
                }
            })
        viewModel.errorLiveData.observe(
            viewLifecycleOwner,
            Observer<Throwable> { throwable ->
                when (throwable) {
                    is UserNotAuthenticated -> navigator.navigateToLoginFragment(this)
                    else -> {
                        snackbar.setText(throwable.message.toString())
                        snackbar.show()
                    }
                }
            })
    }
}