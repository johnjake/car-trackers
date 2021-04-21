package com.cartrackers.app.features.inbox

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.dao.UserDao

class Repository(
    private val userDao: UserDao,
    private val mapper: Mapper): DataSource {

    override suspend fun getInboxList(userId: Int): List<User> {
       return userDao.getInboxList(userId).mapIndexed { _, dbUser ->
            mapper.mapFromRoom(dbUser)
        }
    }
}