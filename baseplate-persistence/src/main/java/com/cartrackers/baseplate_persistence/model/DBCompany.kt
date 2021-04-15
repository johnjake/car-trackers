package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBCompany.tableName)
data class DBCompany(
    @PrimaryKey
    val uid: String,
    val name : String,
    val catchPhrase : String,
    val bs : String
) {
    companion object {
        const val tableName = "company"
    }
    constructor(): this("","", "", "")
}