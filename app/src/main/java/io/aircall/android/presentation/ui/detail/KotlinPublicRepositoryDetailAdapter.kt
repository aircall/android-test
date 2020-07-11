package io.aircall.android.presentation.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.aircall.android.R
import io.aircall.android.databinding.KotlinPublicRepositoryDetailIssueItemBinding
import io.aircall.android.domain.model.IssuesByWeek

internal class KotlinPublicRepositoryDetailAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val issuesByWeeks: MutableList<IssuesByWeek> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.kotlin_public_repository_detail_issue_item, parent, false
        )
        return KotlinPublicRepositoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as KotlinPublicRepositoryViewHolder).onBind(issuesByWeeks[position])
    }

    override fun getItemCount(): Int {
        return issuesByWeeks.size
    }

    fun addData(list: List<IssuesByWeek>) {
        this.issuesByWeeks.clear()
        this.issuesByWeeks.addAll(list)
        notifyDataSetChanged()
    }

    inner class KotlinPublicRepositoryViewHolder(private val dataBinding: ViewDataBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun onBind(issuesByWeek: IssuesByWeek) {
            val binding = dataBinding as KotlinPublicRepositoryDetailIssueItemBinding
            val issuesByWeekViewModel = IssuesByWeekViewModel(issuesByWeek)
            binding.issuesByWeekViewModel = issuesByWeekViewModel
        }
    }
}
