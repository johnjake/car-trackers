package com.cartrackers.app.features.movies.slider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cartrackers.app.data.vo.State
import com.cartrackers.app.data.vo.movies.Discover
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SliderViewModel(
    private val repository: SliderRepository) : ViewModel() {
    private var stateJob: Job? = null
    private val movieFlow = MutableSharedFlow<State<List<Discover>>>()
    val movieState: SharedFlow<State<List<Discover>>> get() = movieFlow

    fun getSliderDiscover() {
        stateJob = viewModelScope.launch {
           val data = repository.sliderNowPlaying()
           val stateData = State.Data(data)
           movieFlow.emit(stateData)
        }
    }

    override fun onCleared() {
        super.onCleared()
        stateJob?.cancel()
    }
}
