package com.cartrackers.baseplate_persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DBWeeklyKeys.table_name)
data class DBWeeklyKeys(
    @PrimaryKey(autoGenerate = true)
    var repoId: Int? = 0,
    val prevKey: Int? = 0,
    val nextKey: Int? = 0) {
    companion object {
        const val table_name = "weekly_keys"
    }
}
