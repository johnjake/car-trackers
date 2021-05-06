package com.cartrackers.app.features.movies.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cartrackers.app.api.ApiServices
import com.cartrackers.app.features.movies.DataSource
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

class SearchRepository(
    private val api: ApiServices,
    private val database: AppDatabase
) : DataSource {

    override fun getMovieSearch(query: String): Flow<PagingData<DBDiscover>> {

        val dbQuery = "%${query.replace(' ', '%')}%"
        val pagingFactory = { database.discoverDao().searchDiscoverByPaging(dbQuery) }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = pagerConfig,
            remoteMediator = mediator(query),
            pagingSourceFactory = pagingFactory
        ).flow
    }

    private val pagerConfig = PagingConfig(pageSize = pagingSize, enablePlaceholders = false)

    private fun mediator(query: String): SearchMediator {
        return SearchMediator(query, api, database)
    }

    companion object {
        const val pagingSize = 10
    }
}
