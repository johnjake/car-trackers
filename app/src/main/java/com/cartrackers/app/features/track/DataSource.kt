package com.cartrackers.app.features.track

import com.cartrackers.app.data.vo.User

interface DataSource {
    suspend fun getListOfDBUser(): List<User>
}