package com.example.mychallenge.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mychallenge.data.local.dao.UserDao
import com.example.mychallenge.domain.module.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserRoomDataBase : RoomDatabase(){
    abstract fun getUserDao(): UserDao

}