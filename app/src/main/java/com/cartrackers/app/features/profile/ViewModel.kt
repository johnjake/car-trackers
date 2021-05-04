package com.cartrackers.app.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository
) : ViewModel() {
        private val userProfileFlow = MutableStateFlow<State<User>>(State.Empty)
        private val listModelFlow = MutableStateFlow<State<List<User>>>(State.Empty)
        val userProfileState: StateFlow<State<User>> = userProfileFlow
        val listModelState: StateFlow<State<List<User>>> = listModelFlow

    fun getUserProfile(userId: Int) {
        viewModelScope.launch {
            val data = repository.getUserDetails(userId)
            val stateData = State.Data(data)
            userProfileFlow.value = stateData
        }
    }

    fun getListFromRoom(userId: Int) {
        viewModelScope.launch {
            val data = repository.getListOfDBUser(userId)
            val stateData = State.Data(data)
            listModelFlow.value = stateData
        }
    }
}
