package com.cartrackers.app.features.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.CarModel
import com.cartrackers.app.data.vo.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: Repository
    ): ViewModel() {
        private val carFlow = MutableStateFlow<State<List<CarModel>>>(State.Empty)
        val carState: StateFlow<State<List<CarModel>>> = carFlow

    fun getCarList() {
        viewModelScope.launch {
            val data = repository.getUserCarList()
            val stateData = State.Data(data)
            carFlow.value = stateData
        }
    }

}