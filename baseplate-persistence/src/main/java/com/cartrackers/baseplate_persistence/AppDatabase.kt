package com.cartrackers.baseplate_persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cartrackers.baseplate_persistence.dao.CountryDao
import com.cartrackers.baseplate_persistence.dao.UserDao
import com.cartrackers.baseplate_persistence.model.*

@Database(
    entities = [DBUser::class, DBCountry::class],
    version = 11,
    exportSchema = false
)
@TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
}