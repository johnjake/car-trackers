package com.cartrackers.app.features.inbox

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
    private val inboxListFlow = MutableStateFlow<State<List<User>>>(State.Empty)
    val inboxState: StateFlow<State<List<User>>> = inboxListFlow

    fun getInboxList(userId: Int) {
        viewModelScope.launch {
            val data = repository.getInboxList(userId)
            val stateData = State.Data(data)
            inboxListFlow.value = stateData
        }
    }
}