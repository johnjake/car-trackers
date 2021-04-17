package com.cartrackers.app.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(
    val lat: Double? = 0.0,
    val lng: Double? = 0.0
): Parcelable
