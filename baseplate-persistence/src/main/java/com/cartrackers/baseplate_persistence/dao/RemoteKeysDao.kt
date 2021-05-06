package com.cartrackers.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBRemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<DBRemoteKeys>)

    @Query("SELECT * FROM movie_keys WHERE repoId = :repoId")
    suspend fun remoteKeysRepoId(repoId: Int): DBRemoteKeys?

    @Query("DELETE FROM movie_keys")
    suspend fun clearRemoteKeys()
}
