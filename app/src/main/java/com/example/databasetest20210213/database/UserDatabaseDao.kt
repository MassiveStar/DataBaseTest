package com.example.databasetest20210213.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user_information_table WHERE userId = :key")
    fun get(key: Long): User?

    @Query("DELETE FROM user_information_table")
    fun clear()

    @Query("SELECT * FROM user_information_table ORDER BY userId DESC")
    fun getAllUsers(): LiveData<List<User>>
}