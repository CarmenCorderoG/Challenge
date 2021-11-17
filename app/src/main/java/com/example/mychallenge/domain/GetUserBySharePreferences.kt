package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.SharePreferencesRepository
import javax.inject.Inject

class GetUserBySharePreferences @Inject constructor(
    private val sharePreferencesRepository: SharePreferencesRepository) {

    fun getUserSharePreferences(key: String): String =
        sharePreferencesRepository.getData(key).toString()
}