package com.cartrackers.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBWeeklyKeys


@Dao
interface WeeklyKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<DBWeeklyKeys>)

    @Query("SELECT * FROM weekly_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): DBWeeklyKeys?

    @Query("DELETE FROM weekly_keys")
    suspend fun clearRemoteKeys()
}
