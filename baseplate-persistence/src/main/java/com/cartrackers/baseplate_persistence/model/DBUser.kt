package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBUser.tableName)
data class DBUser(
    @PrimaryKey(autoGenerate = true)
    val uid: Int? = 0,
    val id: Int? = 0,
    val name: String? = "",
    val username: String? = "",
    var password: String? = "",
    val email: String? = "",
    val address: String? = "",
    val phone: String? = "",
    val website: String? = "",
    val company: String? = ""
) {
    constructor(): this(0, 0, "", "", "", "", "", "", "", "")

    companion object {
        const val tableName = "users"
    }
}