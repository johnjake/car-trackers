package com.cartrackers.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBUpComingKeys

@Dao
interface ComingKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<DBUpComingKeys>)

    @Query("SELECT * FROM upcoming_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): DBUpComingKeys?

    @Query("DELETE FROM upcoming_keys")
    suspend fun clearRemoteKeys()
}
