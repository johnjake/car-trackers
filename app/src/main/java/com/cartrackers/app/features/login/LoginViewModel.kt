package com.cartrackers.app.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
    ): ViewModel() {
        private var stateJob: Job? = null
        private val userFlow = MutableLiveData<State<User?>>(State.Empty)

    fun authenticateUser(userName: String, password: String) {
       stateJob = viewModelScope.launch(Dispatchers.Main) {
            val data = repository.getUserByCredential(userName, password)
            val stateData = State.Data(data)
            userFlow.value = stateData
        }
    }
    val userState = userFlow

    override fun onCleared() {
        super.onCleared()
        stateJob?.cancel()
    }
}