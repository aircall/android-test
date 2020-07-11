package io.aircall.android.presentation.ui.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.aircall.android.OpenForTesting
import io.aircall.android.domain.model.KotlinPublicRepository
import io.aircall.android.domain.model.User
import io.aircall.android.domain.usecase.Result
import io.aircall.android.domain.usecase.repositories.GetTopKotlinPublicRepositoriesUseCase
import io.aircall.android.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@OpenForTesting
class TopKotlinPublicRepositoriesViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getTopKotlinPublicRepositoriesUseCase: GetTopKotlinPublicRepositoriesUseCase
) : ViewModel() {
    private val user = MediatorLiveData<User>()
    private val topKotlinPublicRepositories = MediatorLiveData<List<KotlinPublicRepository>>()
    private val error = MediatorLiveData<Throwable>()
    private val dataLoading = MediatorLiveData<Boolean>()

    val userLiveData: LiveData<User> = user
    val topKotlinPublicRepositoriesLiveData: LiveData<List<KotlinPublicRepository>> =
        topKotlinPublicRepositories
    val errorLiveData: LiveData<Throwable> = error
    val dataLoadingLiveData: LiveData<Boolean> = dataLoading

    fun launchGetUser() {
        viewModelScope.launch {
            getUserUseCase().collect { result ->
                when (result) {
                    is Result.Data -> {
                        user.value = result.data
                        launchGetKotlinPublicRepositories()
                    }
                    is Result.Error -> {
                        error.value = result.error
                    }
                }
            }
        }
    }

    fun launchGetKotlinPublicRepositories() {
        viewModelScope.launch {
            getTopKotlinPublicRepositoriesUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        dataLoading.value = true
                    }
                    is Result.Data -> {
                        topKotlinPublicRepositories.value = result.data
                        dataLoading.value = false
                    }
                    is Result.Error -> {
                        error.value = result.error
                        dataLoading.value = false
                    }
                }
            }
        }
    }
}
