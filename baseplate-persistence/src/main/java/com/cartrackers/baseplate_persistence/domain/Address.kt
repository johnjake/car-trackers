package com.cartrackers.baseplate_persistence.domain

data class Address(
    val street : String,
    val suite : String,
    val city : String,
    val zipcode : String,
    val geo : Coordinates
)