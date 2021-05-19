package com.cartrackers.app.features.movies.view_upcoming

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cartrackers.app.api.ApiServices
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.model.DBUpComing
import kotlinx.coroutines.flow.Flow

class ComingAllRepository(
    private val api: ApiServices,
    private val database: AppDatabase
): ComingAllSource {
    @ExperimentalPagingApi
    override fun getStreamMovies(): Flow<PagingData<DBUpComing>> {
        val pagingFactory = { database.upComingDao().getUpComingByPaging() }
        return Pager(
            config = PagingConfig(pageSize = pagingSize, enablePlaceholders = false),
            remoteMediator = ComingAllMediator(api, database),
            pagingSourceFactory = pagingFactory
        ).flow
    }

    companion object {
        const val pagingSize = 20
    }
}
