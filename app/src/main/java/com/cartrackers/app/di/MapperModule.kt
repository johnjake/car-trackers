package com.cartrackers.app.di

import com.cartrackers.app.data.mapper.Mapper
import org.koin.dsl.module

val mapperModule = module {
    single { providesMapperInstance() }
}

fun providesMapperInstance(): Mapper {
    return Mapper.getInstance()
}
