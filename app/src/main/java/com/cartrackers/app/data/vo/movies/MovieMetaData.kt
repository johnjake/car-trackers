package com.cartrackers.app.data.vo.movies

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieMetaData(
    val page: Int? = 0,
    val total_results: Int? = 0,
    val total_pages: Int? = 0,
    val results: List<Discover> = emptyList(),
    val dates: Dates? = null
) : Parcelable
