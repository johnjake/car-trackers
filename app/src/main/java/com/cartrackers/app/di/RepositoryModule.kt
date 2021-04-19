package com.cartrackers.app.di

import com.cartrackers.app.features.home.Repository as Home
import com.cartrackers.app.features.intro.Repository as Intro
import com.cartrackers.app.features.track.Repository as Track
import org.koin.dsl.module

val repositoryModule = module {
    factory { Home(apiServices = get(), userDao = get(), mapper = get()) }
    factory { Intro(apiServices = get(), userDao = get(), mapper = get()) }
    factory { Track(userDao = get(), mapper = get()) }
}