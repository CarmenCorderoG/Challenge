package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.UserRepository
import com.example.mychallenge.domain.module.User
import javax.inject.Inject

class SetUserRegister @Inject constructor(
    private val userRepository: UserRepository) {

    suspend fun saveUser(user: User){
        userRepository.setInsertUser(user)
    }
}