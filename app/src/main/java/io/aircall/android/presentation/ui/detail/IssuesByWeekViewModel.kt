package io.aircall.android.presentation.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.aircall.android.domain.model.IssuesByWeek

class IssuesByWeekViewModel(issuesByWeek: IssuesByWeek? = null) : ViewModel() {
    val issuesByWeekLiveData = MutableLiveData<IssuesByWeek>()

    init {
        issuesByWeekLiveData.value = issuesByWeek
    }
}