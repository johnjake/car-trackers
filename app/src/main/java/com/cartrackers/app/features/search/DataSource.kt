package com.cartrackers.app.features.search

import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun searchUser(name: String): Flow<List<User>>
}