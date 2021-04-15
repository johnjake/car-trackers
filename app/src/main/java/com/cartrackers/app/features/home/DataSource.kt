package com.cartrackers.app.features.home

import com.cartrackers.app.data.vo.User

interface DataSource {
   suspend fun getListOfUsers(): List<User>
}