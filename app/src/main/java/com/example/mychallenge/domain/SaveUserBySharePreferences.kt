package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.SharePreferencesRepository
import javax.inject.Inject

class SaveUserBySharePreferences @Inject constructor(
    private val sharePreferencesRepository: SharePreferencesRepository) {

    fun saveUserBySharePreferences(key: String, value: String){
        sharePreferencesRepository.putData(key, value)
    }
}