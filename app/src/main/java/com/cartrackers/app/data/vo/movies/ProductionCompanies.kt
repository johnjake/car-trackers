package com.cartrackers.app.data.vo.movies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCompanies(
    val id: Int = 0,
    val logo_path: String? = "",
    val name: String? = "",
    val origin_country: String? = ""
) : Parcelable
