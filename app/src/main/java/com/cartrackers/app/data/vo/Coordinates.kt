package com.cartrackers.app.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(
    val lat : Double,
    val lng : Double
): Parcelable
