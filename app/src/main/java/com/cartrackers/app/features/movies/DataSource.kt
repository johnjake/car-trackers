package com.cartrackers.app.features.movies

import androidx.paging.PagingData
import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getMovieSearch(query: String): Flow<PagingData<DBDiscover>>
}
