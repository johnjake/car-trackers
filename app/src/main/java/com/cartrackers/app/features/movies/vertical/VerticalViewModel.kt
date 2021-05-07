package com.cartrackers.app.features.movies.vertical

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

class VerticalViewModel @ExperimentalPagingApi constructor(
    private val repository: Repository
): ViewModel() {

    @ExperimentalPagingApi
    fun getTopMovies(): Flow<PagingData<DBDiscover>> {
        return repository.getStreamMovies().cachedIn(viewModelScope)
    }
}
