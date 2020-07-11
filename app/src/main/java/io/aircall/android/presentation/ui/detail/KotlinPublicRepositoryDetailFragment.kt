package io.aircall.android.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import io.aircall.android.OpenForTesting
import io.aircall.android.R
import io.aircall.android.databinding.KotlinPublicRepositoryDetailFragmentBinding
import kotlinx.android.synthetic.main.kotlin_public_repository_detail_fragment.*
import javax.inject.Inject

@OpenForTesting
class KotlinPublicRepositoryDetailFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: KotlinPublicRepositoryDetailViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(KotlinPublicRepositoryDetailViewModel::class.java)
    }

    private val adapter: KotlinPublicRepositoryDetailAdapter by lazy {
        KotlinPublicRepositoryDetailAdapter()
    }

    private val args: KotlinPublicRepositoryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: KotlinPublicRepositoryDetailFragmentBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.kotlin_public_repository_detail_fragment,
                container,
                false
            )
        binding.kotlinPublicRepositoryDetailViewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(KotlinPublicRepositoryDetailViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        args.kotlinPublicRepository.run {
            viewModel.setKotlinPublicRepositoryDetail(this)
            repositoryIssuesRecyclerView.adapter = adapter
            adapter.addData(args.kotlinPublicRepository.issuesByWeek)
        }
    }
}