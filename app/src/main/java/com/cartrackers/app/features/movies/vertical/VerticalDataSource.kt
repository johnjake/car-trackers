package com.cartrackers.app.features.movies.vertical

import androidx.paging.PagingData
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

interface VerticalDataSource {
    fun getStreamMovies(): Flow<PagingData<DBDiscover>>
}
