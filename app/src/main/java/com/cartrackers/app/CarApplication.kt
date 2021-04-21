package com.cartrackers.app

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.cartrackers.app.di.*
import com.cartrackers.baseplate_persistence.module.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CarApplication: Application() {
    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CarApplication)
            modules(listOf(
                networkModule,
                storageModule,
                databaseModule,
                mapperModule,
                repositoryModule,
                viewModelModule,
                viewModelLogin
            ))
        }
    }
}