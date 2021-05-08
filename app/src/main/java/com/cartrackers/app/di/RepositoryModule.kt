package com.cartrackers.app.di

import androidx.paging.ExperimentalPagingApi
import com.cartrackers.app.features.movies.upcoming.ComingRepository
import com.cartrackers.app.features.home.Repository as Home
import com.cartrackers.app.features.intro.Repository as Intro
import com.cartrackers.app.features.track.Repository as Track
import com.cartrackers.app.features.profile.Repository as Profile
import com.cartrackers.app.features.cars.Repository as Car
import com.cartrackers.app.features.inbox.Repository as Inbox
import com.cartrackers.app.features.login.Repository as Login
import com.cartrackers.app.features.profile.edit.Repository as EditProfile
import com.cartrackers.app.features.country.Repository as Countries
import com.cartrackers.app.features.profile.register.Repository as Register
import com.cartrackers.app.features.movies.vertical.Repository as Vertical
import com.cartrackers.app.features.movies.week.Repository as Weekly
import org.koin.dsl.module

@ExperimentalPagingApi
val repositoryModule = module {
    factory { Home(apiServices = get(), userDao = get(), mapper = get()) }
    factory { Intro(apiServices = get(), userDao = get(), countryDao = get(), mapper = get()) }
    factory { Track(userDao = get(), mapper = get()) }
    factory { Profile(userDao = get(), mapper = get()) }
    factory { Car() }
    factory { Inbox(userDao = get(), mapper = get()) }
    factory { Login(userDao = get(), mapper = get()) }
    factory { EditProfile(userDao = get()) }
    factory { Countries(dao = get(), mapper = get()) }
    factory { Register(userDao = get(), mapper = get()) }
    factory { Vertical(api = get(), database = get()) }
    factory { Weekly(api = get(), database = get()) }
    factory { ComingRepository(api = get(), database = get()) }
}
