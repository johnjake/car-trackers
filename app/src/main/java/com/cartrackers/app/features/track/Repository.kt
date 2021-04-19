package com.cartrackers.app.features.track

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.dao.UserDao

class Repository(
    private val userDao: UserDao,
    private val mapper: Mapper
): DataSource {

    override suspend fun getListOfDBUser(): List<User> {
        return userDao.getUserList().mapIndexed { _, dbUser ->
           mapper.mapFromRoom(dbUser)
        }
    }
}