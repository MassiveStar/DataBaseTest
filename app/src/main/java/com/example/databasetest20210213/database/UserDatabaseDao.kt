package com.example.databasetest20210213.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user_information_table WHERE userId = :key")
    suspend fun get(key: Long): User?

    @Query("SELECT * FROM user_information_table ORDER BY userId DESC LIMIT 1")
    suspend fun getLastUser(): User?

    @Query("DELETE FROM user_information_table")
    suspend fun clear()

    @Query("SELECT * FROM user_information_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<User>>
}