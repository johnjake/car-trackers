package com.cartrackers.app.features.movies

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.cartrackers.baseplate_persistence.model.DBDiscover
import com.cartrackers.baseplate_persistence.model.DBRemoteKeys

interface PagerItemPosition<T: Any, out D: Any> {
    @ExperimentalPagingApi
    suspend fun cachedPagingResult(page: Int, loadType: LoadType ): RemoteMediator.MediatorResult
    suspend fun getKeyForLastItem(state: PagingState<Int, T>): D?
    suspend fun getKeyForFirstItem(state: PagingState<Int, T>): D?
    suspend fun getKeyToCurrentPosition(state: PagingState<Int, T>): D?
}
