package com.cartrackers.app.di

import com.cartrackers.app.features.home.HomeViewModel as Home
import com.cartrackers.app.features.intro.ViewModel as Intro
import com.cartrackers.app.features.track.ViewModel as Track
import com.cartrackers.app.features.profile.ViewModel as Profile
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { Home(repository = get()) }
    viewModel { Intro(repository = get()) }
    viewModel { Track(repository = get()) }
    viewModel { Profile(repository = get()) }
}