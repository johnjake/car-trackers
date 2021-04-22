package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBCountry.tableName)
data class DBCountry(
    @PrimaryKey(autoGenerate = true)
    val Id: Int? = 0,
    val name: String
) {
    constructor(): this(0, "")
    companion object {
        const val tableName = "countries"
    }
}