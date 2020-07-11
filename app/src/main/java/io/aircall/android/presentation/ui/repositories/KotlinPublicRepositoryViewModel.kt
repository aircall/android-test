package io.aircall.android.presentation.ui.repositories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.aircall.android.domain.model.KotlinPublicRepository

class KotlinPublicRepositoryViewModel(kotlinPublicRepository: KotlinPublicRepository? = null) : ViewModel() {
    val kotlinPublicRepositoryLiveData = MutableLiveData<KotlinPublicRepository>()

    init {
        kotlinPublicRepositoryLiveData.value = kotlinPublicRepository
    }
}