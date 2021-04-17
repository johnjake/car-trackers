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

    private val listDomainFlow = MutableStateFlow<State<List<User>>>(State.Empty)
    private val listModelFlow = MutableStateFlow<State<List<User>>>(State.Empty)

    val listDomainState: StateFlow<State<List<User>>> = listDomainFlow
    val listModelState: StateFlow<State<List<User>>> = listModelFlow

    fun getUserFromDomain() {
        viewModelScope.launch {
            val data = repository.getListOfUsers()
            val stateData = State.Data(data)
            listDomainFlow.value = stateData
        }
    }

    fun getUserFromDb() {
        viewModelScope.launch {
            val data = repository.getListOfDBUser()
            val stateData = State.Data(data)
            listModelFlow.value = stateData
        }
    }

    fun insertUserToDB(user: User, password: String) {
        val encrypted = password.toEncryptedString<String>(encryptionKey)
        viewModelScope.launch {
            repository.insertUserToDao(user, String(encrypted))
        }
    }
}