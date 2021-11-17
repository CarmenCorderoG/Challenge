package com.example.mychallenge.data.repository

import com.example.mychallenge.data.local.database.UserRoomDataBase
import com.example.mychallenge.domain.module.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRoomDataBase: UserRoomDataBase) {

    suspend fun setInsertUser(user: User) = userRoomDataBase.getUserDao().insertUser(user)

    fun getExistUser(mail: String): Flow<User> = userRoomDataBase.getUserDao().getExistUser(mail)

    fun getInfoUser(mail: String, password: String): Flow<User> =
        userRoomDataBase.getUserDao().getUser(mail, password)

}