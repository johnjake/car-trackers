package com.cartrackers.app.features.track

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

    private val listModelFlow = MutableStateFlow<State<List<User>>>(State.Empty)

    val listModelState: StateFlow<State<List<User>>> = listModelFlow

    fun getListFromRoom() {
        viewModelScope.launch {
            val data = repository.getListOfDBUser()
            val stateData = State.Data(data)
            listModelFlow.value = stateData
        }
    }
}