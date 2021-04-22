package com.cartrackers.app.features.country

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class ViewModel(private val repository: Repository): ViewModel() {

    val searchFlow = MutableStateFlow("")

    private val searchRepository = searchFlow.flatMapLatest {
        repository.getCountry(it)
    }

    val searchState = searchRepository.asLiveData()
}