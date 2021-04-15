package com.cartrackers.app

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.cartrackers.app.di.networkModule
import com.cartrackers.app.di.repositoryModule
import com.cartrackers.app.di.viewModelModule
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
                repositoryModule,
                viewModelModule
                /**storageModule,
                appModule
                mapperModule,
                schedulerModule,
                databaseModule,
                viewModelModule,
                implementationModule,
                repositoryModule,
                mediatorModule**/
                /**mapperModule,
                schedulerModule,
                databaseModule,
                viewModelModule,
                implementationModule,
                repositoryModule,
                mediatorModule**/
            ))
        }
    }
}