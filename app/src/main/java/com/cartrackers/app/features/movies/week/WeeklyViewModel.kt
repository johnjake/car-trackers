package com.cartrackers.app.features.movies.week

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cartrackers.baseplate_persistence.model.DBWeekly
import kotlinx.coroutines.flow.Flow

class WeeklyViewModel@ExperimentalPagingApi constructor(
    private val repository: Repository) : ViewModel() {
    @ExperimentalPagingApi
    fun getWeeklyMovies(): Flow<PagingData<DBWeekly>> {
        return repository.getStreamMovies().cachedIn(viewModelScope)
    }
}
