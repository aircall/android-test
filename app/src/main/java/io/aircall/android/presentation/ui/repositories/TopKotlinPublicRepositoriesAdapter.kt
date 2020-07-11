package io.aircall.android.presentation.ui.repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.aircall.android.R
import io.aircall.android.databinding.TopKotlinPublicRepositoriesItemBinding
import io.aircall.android.domain.model.KotlinPublicRepository

internal class TopKotlinPublicRepositoriesAdapter(private val callback: TopKotlinPublicRepositoriesCallback) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val topKotlinPublicRepositoriesList: MutableList<KotlinPublicRepository> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mainListItemBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.top_kotlin_public_repositories_item, parent, false
        )
        return KotlinPublicRepositoryViewHolder(mainListItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as KotlinPublicRepositoryViewHolder).onBind(topKotlinPublicRepositoriesList[position])
    }

    override fun getItemCount(): Int {
        return topKotlinPublicRepositoriesList.size
    }

    fun addData(list: List<KotlinPublicRepository>) {
        this.topKotlinPublicRepositoriesList.clear()
        this.topKotlinPublicRepositoriesList.addAll(list)
        notifyDataSetChanged()
    }

    inner class KotlinPublicRepositoryViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun onBind(kotlinPublicRepository: KotlinPublicRepository) {
            val binding = dataBinding as TopKotlinPublicRepositoriesItemBinding
            val kotlinPublicRepositoryViewModel = KotlinPublicRepositoryViewModel(kotlinPublicRepository)
            binding.kotlinPublicRepositoryViewModel = kotlinPublicRepositoryViewModel
            binding.root.setOnClickListener {
                callback.onKotlinPublicRepositorySelected(kotlinPublicRepository)
            }
        }
    }
}
