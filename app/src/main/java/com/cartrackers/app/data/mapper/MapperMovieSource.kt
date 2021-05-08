package com.cartrackers.app.data.mapper

import com.cartrackers.app.data.vo.movies.Discover
import com.cartrackers.baseplate_persistence.model.DBDiscover
import com.cartrackers.baseplate_persistence.model.DBUpComing
import com.cartrackers.baseplate_persistence.model.DBWeekly

interface MapperMovieSource {
    fun mapFromRoom(from: DBDiscover): Discover
    fun mapFromDomain(from: Discover): DBDiscover
    fun weeklyFromRoom(from: DBWeekly): Discover
    fun weeklyFromDomain(from: Discover): DBWeekly
    fun upFromRoom(from: DBUpComing): Discover
    fun upFromDomain(from: Discover): DBUpComing
}
