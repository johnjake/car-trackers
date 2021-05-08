package com.cartrackers.baseplate_persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBUpComing
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UpComingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieScreen(topRated: DBUpComing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUpComing(topRated: List<DBUpComing>)

    @Query("select * from upcoming_movie group by title")
    abstract fun getWeeklyStreamByTitle(): Flow<List<DBUpComing>>

    @Query("DELETE FROM upcoming_movie")
    abstract suspend fun deleteWeekly()

    @Query("SELECT * FROM upcoming_movie")
    abstract fun getUpComingByPaging(): PagingSource<Int, DBUpComing>
}
