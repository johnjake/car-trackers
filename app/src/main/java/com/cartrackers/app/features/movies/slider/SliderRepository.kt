package com.cartrackers.app.features.movies.slider

import com.cartrackers.app.BuildConfig
import com.cartrackers.app.api.ApiServices
import com.cartrackers.app.data.vo.movies.Discover

@Suppress("NAME_SHADOWING")
class SliderRepository(private val api: ApiServices) : DataSource {
    override suspend fun sliderNowPlaying(): List<Discover> {
        val apiKey = BuildConfig.API_KEY
        return api.getNowPlayingMovies(apiKey, "en-US", 1).results
    }
}
