package com.cartrackers.baseplate_persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cartrackers.baseplate_persistence.dao.*
import com.cartrackers.baseplate_persistence.model.*

@Database(
    entities = [
        DBUser::class,
        DBCountry::class,
        DBDiscover::class,
        DBRemoteKeys::class,
        DBWeekly::class,
        DBWeeklyKeys::class
               ],
    version = 3,
    exportSchema = false
)
@TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun countryDao(): CountryDao
    abstract fun discoverDao(): DiscoverDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun weeklyDao(): WeeklyDao
    abstract fun weeklyKeysDao(): WeeklyKeysDao
}
