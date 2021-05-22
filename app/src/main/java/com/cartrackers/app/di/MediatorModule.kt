package com.cartrackers.app.di

import androidx.paging.ExperimentalPagingApi
import com.cartrackers.app.features.movies.search.SearchMediator
import com.cartrackers.app.features.movies.upcoming.ComingMediator
import com.cartrackers.app.features.movies.vertical.VerticalMediator
import com.cartrackers.app.features.movies.view_upcoming.ComingAllMediator
import com.cartrackers.app.features.movies.week.WeeklyMediator
import org.koin.dsl.module

@ExperimentalPagingApi
val mediatorModule = module {
    factory { VerticalMediator(api = get(), database = get()) }
    factory { WeeklyMediator(api = get(), database = get()) }
    factory { ComingMediator(api = get(), database = get()) }
    factory { ComingAllMediator(api = get(), database = get()) }
    factory { SearchMediator(query = get(), api = get(), database = get()) }
}
