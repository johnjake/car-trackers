package com.cartrackers.app.bases

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState

@ExperimentalPagingApi
abstract class BaseMediator<T : Any, D: Any>() {

    abstract suspend fun getKeyForLastItem(state: PagingState<Int, T>): D?
    abstract suspend fun getKeyForFirstItem(state: PagingState<Int, T>): D?
    abstract suspend fun getKeyToCurrentPosition(state: PagingState<Int, T>): D?
}
