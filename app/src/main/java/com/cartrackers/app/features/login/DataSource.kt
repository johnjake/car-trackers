package com.cartrackers.app.features.login

import com.cartrackers.app.data.vo.User
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun getUserByCredential(authUserName: String, authPassword: String): Flow<User?>
}