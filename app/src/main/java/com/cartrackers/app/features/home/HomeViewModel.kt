package com.cartrackers.app.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.User
import com.cartrackers.app.utils.encryptionKey
import com.cartrackers.app.utils.toEncryptedString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
): ViewModel() {

    private val allUserFlow = MutableStateFlow<State<List<User>>>(State.Empty)

    private val insertFlow = MutableStateFlow<State<User>>(State.Empty)

    val allUserState: StateFlow<State<List<User>>> = allUserFlow

    val insertState: StateFlow<State<User>> = insertFlow

    fun getAllUser() {
        viewModelScope.launch {
            val data = repository.getListOfUsers()
            val stateData = State.Data(data)
            allUserFlow.value = stateData
        }
    }

    fun insertUser(user: User, password: String) {
        val encrypted = password.toEncryptedString<String>(encryptionKey)
        viewModelScope.launch {
            val data = repository.insertUserToDao(user, String(encrypted))
            val stateData = State.Data(data)
            insertFlow.value = stateData as State<User>
        }
    }
}