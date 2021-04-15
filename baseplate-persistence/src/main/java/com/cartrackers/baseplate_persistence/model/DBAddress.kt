package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity

@Entity(tableName = DBAddress.tableAddress)
data class DBAddress(
    val street : String? = "",
    val suite : String? = "",
    val city : String? = "",
    val zipcode : String? = "",
    val geo : String? = ""
){
    companion object {
        const val tableAddress = "address"
    }
    constructor(): this("", "", "", "", "")
}
