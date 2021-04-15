package com.cartrackers.app.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
): ViewModel() {

    private val allUserFlow = MutableStateFlow<State<List<User>>>(State.Empty)

    val allUserState: StateFlow<State<List<User>>> = allUserFlow

    fun getAllUser() {
        viewModelScope.launch {
            val data = repository.getListOfUsers()
            val stateData = State.Data(data)
            allUserFlow.value = stateData
        }
    }
}