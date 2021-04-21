package com.cartrackers.app.features.profile.edit

import com.cartrackers.app.data.vo.Address
import com.cartrackers.app.data.vo.Company

interface DataSource {
    suspend fun updateUserProfile(
        userId: Int,
        name: String,
        username: String,
        email: String,
        address: Address,
        phoneNo: String,
        website: String,
        company: Company
    ): Int
}