package com.cartrackers.app.data.vo.movies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCountries(
    val iso_3166_1 : String? = "",
    val name : String? = ""
) : Parcelable
