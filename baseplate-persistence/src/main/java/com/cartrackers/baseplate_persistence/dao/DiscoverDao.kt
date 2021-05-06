package com.cartrackers.baseplate_persistence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBDiscover
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DiscoverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieScreen(topRated: DBDiscover)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertListMovie(topRated: List<DBDiscover>)

    @Query("select * from movie_discover where uId = :movieId")
    abstract suspend fun getDiscoverById(movieId: Int): DBDiscover

    @Query("select * from movie_discover")
    abstract fun getDiscoverStream(): Flow<List<DBDiscover>>

    @Query("select * from movie_discover group by title")
    abstract fun getDiscoverStreamByTitle(): Flow<List<DBDiscover>>

    @Query("SELECT * FROM movie_discover WHERE id BETWEEN :idStart AND :idEnd")
    abstract fun getDiscoverByRange(idStart: Int, idEnd: Int): List<DBDiscover>

    @Query("SELECT * FROM movie_discover")
    abstract fun getDiscoverByPaging(): PagingSource<Int, DBDiscover>

    @Query("DELETE FROM movie_discover")
    abstract suspend fun deleteDiscover()

    @Query("SELECT * FROM movie_discover WHERE title LIKE :queryString OR original_title LIKE :queryString GROUP BY title ORDER BY title DESC ")
    abstract fun searchDiscoverByPaging(queryString: String): PagingSource<Int, DBDiscover>
}
