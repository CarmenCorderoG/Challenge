package com.example.mychallenge.data.repository

import android.content.SharedPreferences
import javax.inject.Inject

class SharePreferencesRepository @Inject constructor(private val sharePreferences: SharedPreferences) {

    fun putData(key: String, data: String) =
        sharePreferences.edit().putString(key, data).apply()

    fun getData(key: String): String? = sharePreferences.getString(key, "")

    fun getClearSharePreferences() {
        sharePreferences.edit().clear().apply()
    }

}