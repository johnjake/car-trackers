package com.cartrackers.app.features.inbox

import com.cartrackers.app.data.vo.User

interface DataSource {
    suspend fun getInboxList(userId: Int): List<User>
}