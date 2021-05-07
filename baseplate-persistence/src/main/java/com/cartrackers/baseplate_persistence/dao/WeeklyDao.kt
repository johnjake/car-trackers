package com.cartrackers.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBWeekly
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeeklyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWeekly(topRated: List<DBWeekly>)

    @Query("select * from movie_weekly group by title")
    abstract fun getWeeklyStreamByTitle(): Flow<List<DBWeekly>>
}
