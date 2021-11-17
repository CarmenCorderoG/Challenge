package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.SharePreferencesRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(
    private val sharePreferencesRepository: SharePreferencesRepository) {

    fun deleteUser(){
        sharePreferencesRepository.getClearSharePreferences()
    }
}