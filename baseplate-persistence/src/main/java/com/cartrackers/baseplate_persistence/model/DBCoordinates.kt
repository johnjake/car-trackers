package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBCoordinates.tableName)
data class DBCoordinates(
    @PrimaryKey
    val uid: String,
    val lat : Double,
    val lng : Double
) {
    companion object {
        const val tableName = "coordinates"
    }
    constructor(): this("",0.0, 0.0)
}