package com.cartrackers.app.features.search

import com.cartrackers.baseplate_persistence.model.DBUser
import kotlinx.coroutines.flow.Flow

interface DataSource {
    suspend fun searchUser(name: String): Flow<List<DBUser>>
}