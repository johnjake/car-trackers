package com.cartrackers.app.data.mapper

import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.app.extension.toJsonType
import com.cartrackers.app.extension.toListType
import com.cartrackers.baseplate_persistence.model.DBDiscover
import com.cartrackers.baseplate_persistence.model.DBUpComing
import com.cartrackers.baseplate_persistence.model.DBWeekly

class MapperMovie : MapperMovieSource {
    override fun mapFromRoom(from: DBDiscover): Discover {
        return Discover(
            popularity = from.popularity,
            vote_count = from.vote_count,
            video = from.video,
            poster_path = from.poster_path,
            id = from.id,
            adult = from.adult,
            backdrop_path = from.backdrop_path,
            original_language = from.original_language,
            original_title = from.original_title,
            genre_ids = from.genre_ids?.toListType(),
            title = from.title,
            vote_average = from.vote_average,
            overview = from.overview,
            release_date = from.release_date
        )
    }

    override fun mapFromDomain(from: Discover): DBDiscover {
        return DBDiscover(
            popularity = from.popularity,
            vote_count = from.vote_count,
            video = from.video,
            poster_path = from.poster_path,
            id = from.id,
            adult = from.adult,
            backdrop_path = from.backdrop_path,
            original_language = from.original_language,
            original_title = from.original_title,
            genre_ids = from.genre_ids?.toJsonType(),
            title = from.title,
            vote_average = from.vote_average,
            overview = from.overview,
            release_date = from.release_date)
    }

    override fun weeklyFromRoom(from: DBWeekly): Discover {
        return Discover(
            popularity = from.popularity,
            vote_count = from.vote_count,
            video = from.video,
            poster_path = from.poster_path,
            id = from.id,
            adult = from.adult,
            backdrop_path = from.backdrop_path,
            original_language = from.original_language,
            original_title = from.original_title,
            genre_ids = from.genre_ids?.toListType(),
            title = from.title,
            vote_average = from.vote_average,
            overview = from.overview,
            release_date = from.release_date
        )
    }

    override fun weeklyFromDomain(from: Discover): DBWeekly {
        return DBWeekly(
            popularity = from.popularity,
            vote_count = from.vote_count,
            video = from.video,
            poster_path = from.poster_path,
            id = from.id,
            adult = from.adult,
            backdrop_path = from.backdrop_path,
            original_language = from.original_language,
            original_title = from.original_title,
            genre_ids = from.genre_ids?.toJsonType(),
            title = from.title,
            vote_average = from.vote_average,
            overview = from.overview,
            release_date = from.release_date)
    }

    override fun upFromRoom(from: DBUpComing): Discover {
        return Discover(
            popularity = from.popularity,
            vote_count = from.vote_count,
            video = from.video,
            poster_path = from.poster_path,
            id = from.id,
            adult = from.adult,
            backdrop_path = from.backdrop_path,
            original_language = from.original_language,
            original_title = from.original_title,
            genre_ids = from.genre_ids?.toListType(),
            title = from.title,
            vote_average = from.vote_average,
            overview = from.overview,
            release_date = from.release_date
        )
    }

    override fun upFromDomain(from: Discover): DBUpComing {
        return DBUpComing(
            popularity = from.popularity,
            vote_count = from.vote_count,
            video = from.video,
            poster_path = from.poster_path,
            id = from.id,
            adult = from.adult,
            backdrop_path = from.backdrop_path,
            original_language = from.original_language,
            original_title = from.original_title,
            genre_ids = from.genre_ids?.toJsonType(),
            title = from.title,
            vote_average = from.vote_average,
            overview = from.overview,
            release_date = from.release_date)
    }

    companion object {
        private var INSTANCE: MapperMovie? = null

        fun getInstance(): MapperMovie =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: MapperMovie().also { INSTANCE = it }
            }
    }
}
