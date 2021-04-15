package com.cartrackers.app.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackersMetaData(
    val page: Int = 0,
    val total_results: Int = 0,
    val total_pages: Int = 0,
    val results: List<String?>,
    val dates: String
): Parcelable