package com.cartrackers.app.bases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

interface PagerItemPosition<T: Any, out D: Any> {
    @ExperimentalPagingApi
    suspend fun cachedPagingResult(page: Int, loadType: LoadType ): RemoteMediator.MediatorResult
    suspend fun getKeyForLastItem(state: PagingState<Int, T>): D?
    suspend fun getKeyForFirstItem(state: PagingState<Int, T>): D?
    suspend fun getKeyToCurrentPosition(state: PagingState<Int, T>): D?
}
