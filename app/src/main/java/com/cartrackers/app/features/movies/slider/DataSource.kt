package com.cartrackers.app.features.movies.slider

import com.cartrackers.app.data.vo.movies.Discover

interface DataSource {
    suspend fun sliderNowPlaying(): List<Discover>
}
