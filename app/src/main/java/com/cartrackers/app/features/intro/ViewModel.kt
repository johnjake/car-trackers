package com.cartrackers.app.features.intro

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

    private val listDomainFlow = MutableStateFlow<State<List<User>>>(State.Empty)
    val listDomainState: StateFlow<State<List<User>>> = listDomainFlow

    fun insertUserToDB(user: User) {
        viewModelScope.launch {
            repository.insertUserToDao(user)
        }
    }

    fun getUserFromDomain() {
        viewModelScope.launch {
            val data = repository.getListOfUsers()
            val stateData = State.Data(data)
            listDomainFlow.value = stateData
        }
    }
}