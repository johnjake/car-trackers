package com.cartrackers.app.di

import androidx.paging.ExperimentalPagingApi
import com.cartrackers.app.features.movies.vertical.VerticalMediator
import com.cartrackers.app.features.movies.week.WeeklyMediator
import org.koin.dsl.module

@ExperimentalPagingApi
val mediatorModule = module {
    factory { VerticalMediator(api = get(), database = get()) }
    factory { WeeklyMediator(api = get(), database = get()) }
}
