package com.cartrackers.app.di

import com.cartrackers.app.features.home.Repository
import org.koin.dsl.module

val repositoryModule = module {
    factory { Repository(apiServices = get(), userDao = get()) }
}