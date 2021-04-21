package com.cartrackers.app.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int? = 0,
    val name: String? = "",
    val username: String? = "",
    var password: String? = "",
    var email: String? = "",
    val address: Address? = null,
    val phone: String? = "",
    val website: String? = "",
    val company: Company? = null
): Parcelable