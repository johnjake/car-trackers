package com.cartrackers.app.features.movies.vertical

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.cartrackers.app.BuildConfig
import com.cartrackers.app.api.ApiServices
import com.cartrackers.app.data.mapper.MapperMovie
import com.cartrackers.app.bases.PagerItemPosition
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.model.DBDiscover
import com.cartrackers.baseplate_persistence.model.DBRemoteKeys
import retrofit2.HttpException
import java.io.IOException

private const val startingPage = 1
@ExperimentalPagingApi
class VerticalMediator(
    private val api: ApiServices,
    private val database: AppDatabase
): RemoteMediator<Int, DBDiscover>(), PagerItemPosition<DBDiscover, DBRemoteKeys> {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DBDiscover>
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
            val apiResponse = api.getTopRatedMovies(apiKey = apiKey, "eng-US", page)
            val responseItem = apiResponse.results
            val mapper = MapperMovie.getInstance()
            val endOfPaginationReached = responseItem.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.discoverDao().deleteDiscover()
                }
                val prevKeys = if (page == startingPage) null else page - 1
                val nextKeys = if (endOfPaginationReached) null else page + 1
                val keys = responseItem.map {
                    DBRemoteKeys(repoId = it.id, prevKey = prevKeys, nextKey = nextKeys)
                }

                responseItem.forEach { movie ->
                    val map = mapper.mapFromDomain(movie)
                    database.discoverDao().insertMovieScreen(map)
                }
                database.remoteKeysDao().insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    override suspend fun getKeyForLastItem(state: PagingState<Int, DBDiscover>): DBRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                database.remoteKeysDao().remoteKeysRepoId(repo.id)
            }
    }

    override suspend fun getKeyForFirstItem(state: PagingState<Int, DBDiscover>): DBRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { it ->
            database.remoteKeysDao().remoteKeysRepoId(it.id)
        }
    }

    override suspend fun getKeyToCurrentPosition(state: PagingState<Int, DBDiscover>): DBRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { it ->
            database.remoteKeysDao().remoteKeysRepoId(it.id)
        }
    }
}
