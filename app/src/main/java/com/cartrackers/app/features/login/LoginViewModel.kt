package com.cartrackers.app.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
    ): ViewModel() {
        private var stateJob: Job? = null
        private val userFlow = MutableSharedFlow<State<User?>>()
        val userState: SharedFlow<State<User?>> = userFlow

    fun authenticateUser(userName: String, password: String) {
       stateJob = viewModelScope.launch(Dispatchers.Main) {
            repository.getUserByCredential(userName, password).collect { user ->
                val stateData = State.Data(user)
                userFlow.emit(stateData)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        stateJob?.cancel()
    }
}