package com.cartrackers.app.features.movies.week

import androidx.paging.PagingData
import com.cartrackers.baseplate_persistence.model.DBWeekly
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getStreamMovies(): Flow<PagingData<DBWeekly>>
}
