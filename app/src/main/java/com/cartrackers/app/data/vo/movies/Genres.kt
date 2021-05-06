package com.cartrackers.app.data.vo.movies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genres(
    val id : Int = 0,
    val name : String? = ""
) : Parcelable