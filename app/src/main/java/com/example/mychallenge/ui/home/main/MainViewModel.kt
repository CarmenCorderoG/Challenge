package com.example.mychallenge.ui.home.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mychallenge.common.Keys.Companion.PREF_USER
import com.example.mychallenge.domain.GetUserBySharePreferences
import com.example.mychallenge.domain.module.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserBySharePreferences: GetUserBySharePreferences):  ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    init {
        getInfoUser()
    }

    fun getInfoUser() {
        val jsonUser = Gson().fromJson(getUserBySharePreferences.getUserSharePreferences(PREF_USER), User::class.java)
        if(jsonUser != null)
            _user.postValue(jsonUser)

    }

}