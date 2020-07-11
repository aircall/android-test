package io.aircall.android.presentation.main

import android.view.View
import androidx.lifecycle.*
import io.aircall.android.OpenForTesting
import io.aircall.android.domain.model.User
import io.aircall.android.domain.usecase.Result
import io.aircall.android.domain.usecase.user.GetUserUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@OpenForTesting
class MainViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val authState = MediatorLiveData<AuthState>()
    private val user = MediatorLiveData<User>()
    private val error = MediatorLiveData<Throwable>()

    val authStateLiveData: LiveData<AuthState> = authState
    val userLiveData: LiveData<User> = user
    val errorLiveData: LiveData<Throwable> = error

    fun launchGetUser() {
        viewModelScope.launch {
            getUserUseCase().collect { result ->
                when (result) {
                    is Result.Data -> {
                        user.value = result.data
                        authState.value = AuthState.LOGGED
                    }
                    is Result.Error -> {
                        error.value = result.error
                        authState.value = AuthState.UNLOGGED
                    }
                }
            }
        }
    }

    enum class AuthState(val userNameVisibility: Int, val loginButtonVisibility: Int) {
        LOGGED(View.VISIBLE, View.GONE),
        UNLOGGED(View.GONE, View.VISIBLE);
    }
}
