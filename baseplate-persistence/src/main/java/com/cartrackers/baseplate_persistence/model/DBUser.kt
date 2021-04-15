package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity

@Entity(tableName = DBUser.tableName)
data class DBUser(
    val id: Int? = 0,
    val name: String? = "",
    val username: String? = "",
    val password: String? = "",
    val email: String? = "",
    val address: String?,
    val phone: String,
    val website: String,
    val company: String
) {
    companion object {
        const val tableName = "users"
    }
    constructor(): this(0, "", "", "", "", "", "", "", "")
}