package com.cartrackers.app.features.search

import com.cartrackers.baseplate_persistence.dao.UserDao
import com.cartrackers.baseplate_persistence.model.DBUser
import kotlinx.coroutines.flow.Flow

class Repository(private val dao: UserDao): DataSource {
    override suspend fun searchUser(name: String): Flow<List<DBUser>> = dao.searchUser(name)
}