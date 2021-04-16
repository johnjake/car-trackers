package com.cartrackers.baseplate_persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrackers.baseplate_persistence.model.DBUser
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertUser(user: DBUser)

    @Query("select * from users where id = :userId")
    abstract suspend fun getUserDetails(userId: Int): DBUser

    @Query("select * from users where username = :authUserName and password = :authPassword")
    abstract suspend fun getUserByCredential(authUserName: String, authPassword: String): DBUser

    @Query("select * from users where name like '%' || :searchItem || '%' ")
    abstract fun searchUser(searchItem: String): Flow<List<DBUser>>

    @Query("select * from users where name like '%' || :searchItem || '%' ")
    abstract suspend fun searchListUser(searchItem: String): List<DBUser>

    @Query("select * from users")
    abstract fun getUserListFlow(): Flow<List<DBUser>>

    @Query("select * from users")
    abstract suspend fun getUserList(): List<DBUser>
}