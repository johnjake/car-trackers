package com.cartrackers.app.features.movies

import androidx.paging.PagingState
import com.cartrackers.baseplate_persistence.model.DBDiscover
import com.cartrackers.baseplate_persistence.model.DBRemoteKeys

interface PagingPosition {
    suspend fun getKeyForLastItem(state: PagingState<Int, DBDiscover>): DBRemoteKeys?
    suspend fun getKeyForFirstItem(state: PagingState<Int, DBDiscover>): DBRemoteKeys?
    suspend fun getKeyToCurrentPosition(state: PagingState<Int, DBDiscover>): DBRemoteKeys?
}
