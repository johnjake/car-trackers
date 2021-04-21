package com.cartrackers.app.features.login

import com.cartrackers.app.data.vo.User

interface DataSource {
    suspend fun getUserByCredential(authUserName: String, authPassword: String): User?
}