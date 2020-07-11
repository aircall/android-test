package io.aircall.android.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.aircall.android.OpenForTesting
import io.aircall.android.domain.model.KotlinPublicRepository
import javax.inject.Inject


@OpenForTesting
class KotlinPublicRepositoryDetailViewModel @Inject constructor() : ViewModel() {
    private val kotlinPublicRepository = MediatorLiveData<KotlinPublicRepository>()

    val kotlinPublicRepositoryLiveData: LiveData<KotlinPublicRepository> = kotlinPublicRepository

    fun setKotlinPublicRepositoryDetail(kotlinPublicRepository: KotlinPublicRepository) {
        this.kotlinPublicRepository.value = kotlinPublicRepository
    }
}
