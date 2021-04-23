package com.cartrackers.app.features.profile.register

import com.cartrackers.app.data.vo.User

interface DataSource {
    suspend fun insertUserToDao(user: User)
}