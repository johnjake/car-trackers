package com.cartrackers.app.features.movies.upcoming

import androidx.paging.PagingData
import com.cartrackers.baseplate_persistence.model.DBUpComing
import kotlinx.coroutines.flow.Flow

interface ComingSource {
    fun getStreamMovies(): Flow<PagingData<DBUpComing>>
}
