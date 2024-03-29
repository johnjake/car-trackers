package com.cartrackers.app.features.home

import android.util.Log
import com.cartrackers.app.api.ApiServices
import com.cartrackers.app.data.mapper.Mapper
import com.cartrackers.app.data.vo.User
import com.cartrackers.baseplate_persistence.dao.UserDao
import java.lang.Exception

class Repository(
    private val apiServices: ApiServices,
    private val userDao: UserDao,
    private val mapper: Mapper
    ): DataSource {

    override suspend fun getListOfUsers(): List<User> = apiServices.getAllUsers()

    override suspend fun insertUserToDao(user: User) {
        try {
            val model = mapper.mapFromDomain(user)
            userDao.insertUserQuery(
                id = model.id ?: 0,
                name = model.name ?: "",
                username = model.username ?: "",
                password = model.password ?: "",
                email = model.email ?: "",
                address = model.address ?: "",
                phone = model.phone ?: "",
                website = model.website ?: "",
                company = model.company ?: ""
            )
        } catch (e: Exception) {
            Log.e("Error: ",  "${e.message}")
        }
    }

    override suspend fun getListOfDBUser(): List<User> {
        var list: List<User> = emptyList()
         userDao.getUserList().map {
            val user = mapper.mapFromRoom(it)
             list = listOf(user)
         }
         return list
    }
}