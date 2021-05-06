package com.cartrackers.app.features.movies.vertical

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cartrackers.app.api.ApiServices
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

class Repository(private val api: ApiServices, private val database: AppDatabase) : VerticalDataSource {
    override fun getStreamMovies(): Flow<PagingData<DBDiscover>> {
        val pagingFactory = { database.discoverDao().getDiscoverByPaging() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = pagingSize, enablePlaceholders = false),
            remoteMediator = VerticalMediator(api, database),
            pagingSourceFactory = pagingFactory
        ).flow
    }
    companion object {
        const val pagingSize = 20
    }
}
