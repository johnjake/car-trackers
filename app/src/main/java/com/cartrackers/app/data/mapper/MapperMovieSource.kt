package com.cartrackers.app.data.mapper

import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.baseplate_persistence.model.DBDiscover

interface MapperMovieSource {
    fun mapFromRoom(from: DBDiscover): Discover
    fun mapFromDomain(from: Discover): DBDiscover
}
