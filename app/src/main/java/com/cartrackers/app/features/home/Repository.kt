package com.cartrackers.app.features.home

import com.cartrackers.app.api.ApiServices
import com.cartrackers.app.data.vo.User

class Repository(private val apiServices: ApiServices): DataSource {
    override suspend fun getListOfUsers(): List<User> = apiServices.getAllUsers()
}