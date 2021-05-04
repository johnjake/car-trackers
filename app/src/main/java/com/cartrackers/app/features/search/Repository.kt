package com.cartrackers.app.features.search

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.dao.UserDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(
    private val dao: UserDao,
    private val mapper: Mapper
) : DataSource {

    override fun searchUser(name: String): Flow<List<User>> = flow  {
        val searchResult = dao.searchListUser(name)
        for (i in 1..searchResult.count()) {
            val user = mapper.mapFromRoom(searchResult[i])
            emit(listOf(user))
        }
    }
}
