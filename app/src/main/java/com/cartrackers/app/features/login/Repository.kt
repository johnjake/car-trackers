package com.cartrackers.app.features.login

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.dao.UserDao
import java.lang.Exception

class Repository(private val userDao: UserDao, private val mapper: Mapper): DataSource {
    override suspend fun getUserByCredential(authUserName: String, authPassword: String): User? {
        val user = userDao.getUserByCredential(authUserName, authPassword)
        return user?.let { mapper.mapFromRoom(it) }
    }
}