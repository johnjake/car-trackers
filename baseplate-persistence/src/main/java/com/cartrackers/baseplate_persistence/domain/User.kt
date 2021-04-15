package com.cartrackers.baseplate_persistence.domain

data class User(
    val id: Int? = 0,
    val name: String? = "",
    val username: String? = "",
    val password: String? = "",
    val email: String? = "",
    val address: Address?,
    val phone: String,
    val website: String,
    val company: Company
)