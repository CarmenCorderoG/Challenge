package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.UserRepository
import com.example.mychallenge.domain.module.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetValidExistUser @Inject constructor(
    private val userRepository: UserRepository) {

    fun getUserInfo(mail: String): Flow<User> {
        return userRepository.getExistUser(mail)
    }
}