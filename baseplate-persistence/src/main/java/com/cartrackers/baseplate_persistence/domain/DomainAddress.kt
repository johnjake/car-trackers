package com.cartrackers.baseplate_persistence.domain

data class DomainAddress(
    val street : String,
    val suite : String,
    val city : String,
    val zipcode : String,
    val geo : DomainCoordinates
)