package com.cartrackers.baseplate_persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBWeekly
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeeklyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieScreen(topRated: DBWeekly)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertWeekly(topRated: List<DBWeekly>)

    @Query("select * from movie_weekly group by title")
    abstract fun getWeeklyStreamByTitle(): Flow<List<DBWeekly>>

    @Query("DELETE FROM movie_weekly")
    abstract suspend fun deleteWeekly()

    @Query("SELECT * FROM movie_weekly")
    abstract fun getDiscoverByPaging(): PagingSource<Int, DBWeekly>
}
