package com.cartrackers.app.features.profile.edit

import com.cartrackers.app.data.vo.Address
import com.cartrackers.app.data.vo.Company
import com.cartrackers.app.extension.toJsonType
import com.cartrackers.baseplate_persistence.dao.UserDao
import java.lang.Exception

class Repository(
    private val userDao: UserDao): DataSource {
    override suspend fun updateUserProfile(
        userId: Int,
        name: String,
        username: String,
        email: String,
        address: Address,
        phoneNo: String,
        website: String,
        company: Company
    ): Int {
        try {
            val formattedAddress = address.toJsonType().toString()
            val formattedCompany = company.toJsonType().toString()
            userDao.updateUserProfile(
                userId,
                name,
                username,
                email,
                formattedAddress,
                phoneNo,
                website,
                formattedCompany
            )
            return 1
        }catch (ex: Exception) {
            return 0
        }
    }
}