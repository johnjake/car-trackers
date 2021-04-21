package com.cartrackers.app.features.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.Address
import com.cartrackers.app.data.vo.Company
import com.cartrackers.app.data.vo.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository
): ViewModel() {
    private var editedFlow = MutableLiveData<State<Int>>(State.Empty)
    val editedState: LiveData<State<Int>> = editedFlow
    private var stateJob: Job? = null

    fun updateProfile(
        userId: Int,
        name: String,
        username: String,
        email: String,
        address: Address,
        phoneNo: String,
        website: String,
        company: Company
    ) {
        stateJob = viewModelScope.launch {
            val data = repository.updateUserProfile(userId, name, username, email, address, phoneNo, website, company)
            val stateData = State.Data(data)
            editedFlow.value = stateData
        }
    }

    override fun onCleared() {
        super.onCleared()
        stateJob?.cancel()
    }

}