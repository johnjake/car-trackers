package com.cartrackers.app.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository
    ): ViewModel() {
        private val userFlow = MutableStateFlow<State<User>>(State.Empty)
        val userState: StateFlow<State<User>> = userFlow

    fun authenticateUser(userName: String, password: String) {
        viewModelScope.launch {
            val data = repository.getUserByCredential(userName, password)
            val stateData = State.Data(data)
            userFlow.value = stateData
        }
    }
}