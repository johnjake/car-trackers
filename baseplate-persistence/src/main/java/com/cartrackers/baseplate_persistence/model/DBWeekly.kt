package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBWeekly.table_name)
data class DBWeekly(
    @PrimaryKey(autoGenerate = true)
    val uId: Int = 0,
    val id: Int = 0,
    val popularity: Double = 0.0,
    val vote_count: Int = 0,
    val video: Boolean = false,
    val poster_path: String? = "",
    val adult: Boolean = false,
    val backdrop_path: String? = "",
    val original_language: String? = "",
    val original_title: String? = "",
    val genre_ids: String? = "",
    val title: String? = "",
    val vote_average: Double = 0.0,
    val overview: String? = "",
    val release_date: String = ""
) {
    constructor() : this(
        uId  = 0,
        id  = 0,
        popularity = 0.0,
        vote_count = 0,
        video = false,
        poster_path  = "",
        adult = false,
        backdrop_path = "",
        original_language = "",
        original_title = "",
        genre_ids = "",
        title = "",
        vote_average = 0.0,
        overview = "",
        release_date = ""
    )
    companion object {
        const val table_name = "movie_weekly"
    }
}
