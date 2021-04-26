package com.cartrackers.app.features.login

import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.baseplate_persistence.dao.UserDao
import kotlinx.coroutines.flow.flow

class Repository(private val userDao: UserDao, private val mapper: Mapper): DataSource {
    override fun getUserByCredential(
        authUserName: String,
        authPassword: String
    ) = flow {
        val user = userDao.getVerifiedUser(authUserName, authPassword)
        emit(user?.let { mapper.mapFromRoom(it) })
    }
}