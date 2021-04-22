package com.cartrackers.app.features.profile.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository
    ): ViewModel() {

    private var stateJob: Job? = null

    fun insertUserToDB(user: User) {
       stateJob = viewModelScope.launch(Dispatchers.Main) {
            repository.insertUserToDao(user)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stateJob?.cancel()
    }

}