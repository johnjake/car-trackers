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

    @Query("insert into users( id, name, username, password, email, address, phone, website, company ) select :id, :name, :username, :password, :email, :address, :phone, :website, :company  where not exists ( select id from users where id = :id)")
    abstract suspend fun insertUserQuery(id: Int, name: String, username: String, password: String, email: String, address: String, phone: String, website: String, company: String)

    @Query("select * from users where id = :userId")
    abstract suspend fun getUserDetails(userId: Int): DBUser

    @Query("select * from users where email = :authUserName and password = :authPassword")
    abstract suspend fun getUserByCredential(authUserName: String, authPassword: String): DBUser?

    @Query("update users set name = :name, username = :username, email = :email, address = :address, phone = :phoneNo, website = :website, company = :company where id = :userId")
    abstract suspend fun updateUserProfile(
        userId: Int,
        name: String,
        username: String,
        email: String,
        address: String,
        phoneNo: String,
        website: String,
        company: String
    )

    @Query("select * from users where name like '%' || :searchItem || '%' ")
    abstract fun searchUser(searchItem: String): Flow<List<DBUser>>

    @Query("select * from users where name like '%' || :searchItem || '%' ")
    abstract suspend fun searchListUser(searchItem: String): List<DBUser>

    @Query("select * from users")
    abstract fun getUserListFlow(): Flow<List<DBUser>>

    @Query("select * from users where id <> :userId")
    abstract suspend fun getFriendsList(userId: Int): List<DBUser>

    @Query("select * from users where id <> :userId")
    abstract suspend fun getInboxList(userId: Int): List<DBUser>

    @Query("select * from users")
    abstract suspend fun getUserList(): List<DBUser>
}