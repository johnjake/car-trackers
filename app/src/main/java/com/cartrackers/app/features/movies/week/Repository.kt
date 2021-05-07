package com.cartrackers.app.features.movies.week

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cartrackers.app.api.ApiServices
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.model.DBWeekly
import kotlinx.coroutines.flow.Flow

class Repository(
    private val api: ApiServices,
    private val database: AppDatabase) : DataSource {

    @ExperimentalPagingApi
    override fun getStreamMovies(): Flow<PagingData<DBWeekly>> {
        val pagingFactory = { database.weeklyDao().getDiscoverByPaging() }
        return Pager(
            config = PagingConfig(pageSize = pagingSize, enablePlaceholders = false),
            remoteMediator = WeeklyMediator(api, database),
            pagingSourceFactory = pagingFactory
        ).flow
    }
    companion object {
        const val pagingSize = 20
    }
}
