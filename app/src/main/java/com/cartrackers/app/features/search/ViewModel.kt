package com.cartrackers.app.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class ViewModel(
    private val repository: Repository
    ): ViewModel() {

    private val searchQuery = MutableStateFlow("")

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    private val userFlow = searchQuery.flatMapLatest {
        repository.searchUser(it)
    }

    //val userQuery = userFlow.asLiveData()

}