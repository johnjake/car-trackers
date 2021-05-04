package com.cartrackers.app.features.profile

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.dao.UserDao

class Repository(
    private val userDao: UserDao,
    private val mapper: Mapper
) : DataSource {
    override suspend fun getUserDetails(userId: Int): User {
        val result = userDao.getUserDetails(userId)
        return mapper.mapFromRoom(result)
    }

    override suspend fun getListOfDBUser(userId: Int): List<User> {
        return userDao.getFriendsList(userId).mapIndexed { _, dbUser ->
            mapper.mapFromRoom(dbUser)
        }
    }
}
