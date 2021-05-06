package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBRemoteKeys.table_name)
data class DBRemoteKeys(
    @PrimaryKey(autoGenerate = true)
    var repoId: Int? = 0,
    val prevKey: Int? = 0,
    val nextKey: Int? = 0) {
    companion object {
        const val table_name = "movie_keys"
    }
}
