package com.cartrackers.baseplate_persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cartrackers.baseplate_persistence.dao.UserDao
import com.cartrackers.baseplate_persistence.model.DBAddress
import com.cartrackers.baseplate_persistence.model.DBCompany
import com.cartrackers.baseplate_persistence.model.DBCoordinates
import com.cartrackers.baseplate_persistence.model.DBUser

@Database(
    entities = [DBUser::class],
    version = 8,
    exportSchema = false
)
@TypeConverters
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}