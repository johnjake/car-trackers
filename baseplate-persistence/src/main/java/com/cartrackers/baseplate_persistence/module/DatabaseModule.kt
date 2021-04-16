package com.cartrackers.baseplate_persistence.module

import android.app.Application
import androidx.room.Room
import com.cartrackers.baseplate_persistence.AppDatabase
import com.cartrackers.baseplate_persistence.dao.UserDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "cartrackers")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideUserDao(database: AppDatabase): UserDao {
        return  database.userDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideUserDao(get()) }
}