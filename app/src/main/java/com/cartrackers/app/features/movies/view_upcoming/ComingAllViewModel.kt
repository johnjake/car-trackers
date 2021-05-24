package com.cartrackers.app.features.movies.view_upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cartrackers.baseplate_persistence.model.DBUpComing
import kotlinx.coroutines.flow.Flow

class ComingAllViewModel @ExperimentalPagingApi constructor(
    private val repository: ComingAllRepository
): ViewModel() {

    @ExperimentalPagingApi
    fun getTopMovies(): Flow<PagingData<DBUpComing>> {
        return repository.getStreamMovies().cachedIn(viewModelScope)
    }
}
