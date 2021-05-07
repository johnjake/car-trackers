package com.cartrackers.app.features.movies.week

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.api.ApiServices
import com.cartrackers.app.bases.PagerItemPosition
import com.cartrackers.app.data.mapper.MapperMovie
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.model.DBWeekly
import com.cartrackers.baseplate_persistence.model.DBWeeklyKeys
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class WeeklyMediator(
    private val api: ApiServices,
    private val database: AppDatabase
) : RemoteMediator<Int, DBWeekly>(), PagerItemPosition<DBWeekly, DBWeeklyKeys> {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DBWeekly>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getKeyToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: startingPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = getKeyForFirstItem(state)
                val previousKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                previousKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }
        return cachedPagingResult(page, loadType)
    }

    override suspend fun cachedPagingResult(page: Int, loadType: LoadType): MediatorResult {
        try {
            val apiKey = BuildConfig.API_KEY
            val apiResponse = api.getWeeklyMovies(apiKey = apiKey, page)
            val responseItem = apiResponse.results
            val mapper = MapperMovie.getInstance()
            val endOfPaginationReached = responseItem.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.weeklyKeysDao().clearRemoteKeys()
                    database.weeklyDao().deleteWeekly()
                }
                val prevKeys = if (page == startingPage) null else page - 1
                val nextKeys = if (endOfPaginationReached) null else page + 1
                val keys = responseItem.map {
                    DBWeeklyKeys(repoId = it.id, prevKey = prevKeys, nextKey = nextKeys)
                }

                responseItem.forEach { movie ->
                    val map = mapper.weeklyFromDomain(movie)
                    database.weeklyDao().insertMovieScreen(map)
                }
                database.weeklyKeysDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun getKeyForLastItem(state: PagingState<Int, DBWeekly>): DBWeeklyKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                database.weeklyKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    override suspend fun getKeyForFirstItem(state: PagingState<Int, DBWeekly>): DBWeeklyKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { it ->
            database.weeklyKeysDao().remoteKeysRepoId(it.id)
        }
    }

    override suspend fun getKeyToCurrentPosition(state: PagingState<Int, DBWeekly>): DBWeeklyKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { it ->
            database.weeklyKeysDao().remoteKeysRepoId(it.id)
        }
    }

    companion object {
        const val startingPage = 1
    }
}
