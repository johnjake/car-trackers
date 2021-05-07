package com.cartrackers.app.features.movies.search

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {
    private var currentQuery: String? = null
    private var currentResult: Flow<PagingData<DBDiscover>>? = null

    @ExperimentalPagingApi
    fun searchMovie(query: String): Flow<PagingData<DBDiscover>> {
        val lastResult = currentResult
        if (query == currentQuery && lastResult != null) {
            return lastResult
        }
        currentQuery = query
        val newResult: Flow<PagingData<DBDiscover>> = repository.getMovieSearch(query)
        currentResult = newResult
        return newResult
    }
}
