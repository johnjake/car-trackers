package com.cartrackers.app.features.movies.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cartrackers.baseplate_persistence.model.DBUpComing
import kotlinx.coroutines.flow.Flow

class ComingViewModel @ExperimentalPagingApi constructor(
    private val repository: ComingRepository
): ViewModel() {

    @ExperimentalPagingApi
    fun getTopMovies(): Flow<PagingData<DBUpComing>> {
        return repository.getStreamMovies().cachedIn(viewModelScope)
    }
}
