package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity

@Entity(tableName = DBCompany.tableName)
data class DBCompany(
    val name : String,
    val catchPhrase : String,
    val bs : String
) {
    companion object {
        const val tableName = "company"
    }
    constructor(): this("", "", "")
}