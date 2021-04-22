package com.cartrackers.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBCountry
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CountryDao {

    @Query("insert into countries(name) select :name where not exists ( select name from countries where name = :name)")
    abstract suspend fun insertCountry(name: String)

    @Query("select * from countries where name like '%' || :searchItem || '%'")
    abstract fun getCountry(searchItem: String): Flow<List<DBCountry>>
}