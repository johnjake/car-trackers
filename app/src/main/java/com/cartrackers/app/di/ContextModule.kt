package com.cartrackers.app.di

import com.cartrackers.app.CarApplication
import org.koin.dsl.module

val contextModule = module {
    single { provideAndroidContext() }
}

fun provideAndroidContext(): CarApplication {
   return CarApplication()
}