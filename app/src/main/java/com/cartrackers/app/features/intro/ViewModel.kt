package com.cartrackers.app.features.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository
    ): ViewModel() {

    private var stateJob: Job? = null

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

    fun insertCountryToDB() {
       stateJob = viewModelScope.launch(Dispatchers.Main) {
           repository.insertCountryDao()
       }
    }

    override fun onCleared() {
        super.onCleared()
        stateJob?.cancel()
    }
}