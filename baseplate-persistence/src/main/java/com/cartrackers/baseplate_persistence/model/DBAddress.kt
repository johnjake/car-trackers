package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBAddress.tableAddress)
data class DBAddress(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val street : String? = "",
    val suite : String? = "",
    val city : String? = "",
    val zipcode : String? = "",
    val geo : String? = ""
){
    companion object {
        const val tableAddress = "address"
    }
    constructor(): this(0, "", "", "", "", "")
}
