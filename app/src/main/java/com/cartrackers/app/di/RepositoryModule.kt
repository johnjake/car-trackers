package com.cartrackers.app.di

import com.cartrackers.app.features.home.Repository as Home
import com.cartrackers.app.features.intro.Repository as Intro
import com.cartrackers.app.features.track.Repository as Track
import com.cartrackers.app.features.profile.Repository as Profile
import com.cartrackers.app.features.cars.Repository as Car
import com.cartrackers.app.features.inbox.Repository as Inbox
import org.koin.dsl.module

val repositoryModule = module {
    factory { Home(apiServices = get(), userDao = get(), mapper = get()) }
    factory { Intro(apiServices = get(), userDao = get(), mapper = get()) }
    factory { Track(userDao = get(), mapper = get()) }
    factory { Profile(userDao = get(), mapper = get()) }
    factory { Car() }
    factory { Inbox(userDao = get(), mapper = get()) }
    factory { com.cartrackers.app.features.login.Repository(userDao = get(), mapper = get()) }
}