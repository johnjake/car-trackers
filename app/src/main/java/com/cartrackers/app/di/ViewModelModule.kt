package com.cartrackers.app.di

import com.cartrackers.app.features.home.HomeViewModel as Home
import com.cartrackers.app.features.intro.ViewModel as Intro
import com.cartrackers.app.features.track.ViewModel as Track
import com.cartrackers.app.features.profile.ViewModel as Profile
import com.cartrackers.app.features.cars.ViewModel as Car
import com.cartrackers.app.features.inbox.ViewModel as Inbox
import com.cartrackers.app.features.login.LoginViewModel as Login
import com.cartrackers.app.features.profile.edit.ViewModel as EditedProfile
import com.cartrackers.app.features.country.ViewModel as Countries
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { Home(repository = get()) }
    viewModel { Intro(repository = get()) }
    viewModel { Track(repository = get()) }
    viewModel { Profile(repository = get()) }
    viewModel { Car(repository = get()) }
    viewModel { Inbox(repository = get()) }
    viewModel { Login(repository = get()) }
    viewModel { EditedProfile(repository = get()) }
    viewModel { Countries(repository = get()) }
}
