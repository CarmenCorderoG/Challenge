package com.example.mychallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mychallenge.domain.module.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT COUNT(*) FROM User")
    fun countAllUsers(): Flow<Int>

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE email = :mail")
    fun getExistUser(mail: String): Flow<User>

    @Query("SELECT * FROM User WHERE email = :mail AND password = :password")
    fun getUser(mail: String, password: String): Flow<User>

}