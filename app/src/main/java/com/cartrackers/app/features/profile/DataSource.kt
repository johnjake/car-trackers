package com.cartrackers.app.features.profile

import com.cartrackers.app.data.vo.User

interface DataSource {
    suspend fun getUserDetails(userId: Int): User
}