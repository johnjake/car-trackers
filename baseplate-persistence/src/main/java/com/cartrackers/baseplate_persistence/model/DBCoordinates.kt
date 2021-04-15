package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity

@Entity(tableName = DBCoordinates.tableName)
data class DBCoordinates(
    val lat : Double,
    val lng : Double
) {
    companion object {
        const val tableName = "coordinates"
    }
    constructor(): this(0.0, 0.0)
}