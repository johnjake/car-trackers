package com.cartrackers.app.api

import com.cartrackers.app.data.vo.User
import retrofit2.http.GET

interface ApiServices {
    @GET("/users")
    suspend fun getAllUsers(): List<User>
}