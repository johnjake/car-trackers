package com.cartrackers.app.features.movies.view_upcoming

import androidx.paging.PagingData
import com.cartrackers.baseplate_persistence.model.DBUpComing
import kotlinx.coroutines.flow.Flow

interface ComingAllSource {
    fun getStreamMovies(): Flow<PagingData<DBUpComing>>
}
