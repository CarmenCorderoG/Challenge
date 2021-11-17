package com.example.mychallenge.ui.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mychallenge.common.Extensions.isEmailValid
import com.example.mychallenge.common.Extensions.isEqualPassword
import com.example.mychallenge.common.Extensions.isNameValid
import com.example.mychallenge.common.Extensions.isPasswordValid
import com.example.mychallenge.common.Keys
import com.example.mychallenge.common.StatusUser
import com.example.mychallenge.domain.GetCanAuthenticateByBiometric
import com.example.mychallenge.domain.GetValidExistUser
import com.example.mychallenge.domain.SetUserRegister
import com.example.mychallenge.domain.module.User
import com.example.mychallenge.domain.SaveUserBySharePreferences
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val getExistUser: GetValidExistUser,
    private val setUserRegister: SetUserRegister,
    private val SaveUserBySharePreferences: SaveUserBySharePreferences): ViewModel() {

    private val _validInfoMsg = MutableLiveData<StatusUser>()
    val validInfoMsg: LiveData<StatusUser> get() = _validInfoMsg

    private val _oldUser = MutableLiveData<User>()
    val oldUser: LiveData<User> get() = _oldUser

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _userRegisterSuccess = MutableLiveData<User>()
    val userRegisterSuccess: LiveData<User> get() = _userRegisterSuccess

    private val _executor = MutableLiveData<Executor>()
    val executor: LiveData<Executor> get() = _executor

    init {
        _loading.postValue(false)
    }

    fun validInfoRegisterUser(name: String, mail: String, password: String, passwordAgain: String){
        _loading.postValue(true)
        _validInfoMsg.value = when {
            name.isNameValid() == StatusUser.NAME_ERROR -> name.isNameValid()
            mail.isEmailValid() == StatusUser.MAIL_ERROR -> mail.isEmailValid()
            password.isPasswordValid() == StatusUser.PASSWORD_ERROR -> password.isPasswordValid()
            password.isEqualPassword(passwordAgain) == StatusUser.PASSWORD_NOT_THE_SAME -> StatusUser.PASSWORD_NOT_THE_SAME
            else -> StatusUser.INFO_SUCCESS
        }

        _loading.postValue(false)
    }


    fun getUserExist(mail: String){
        _loading.postValue(true)
        viewModelScope.launch {
            getExistUser.getUserInfo(mail).collect { oldUser ->
                _loading.postValue(false)
                _oldUser.postValue(oldUser)
            }
        }
    }


    fun getRegisterUser(name: String, mail: String, password: String){
        val user = User(name, mail, password)
        viewModelScope.launch {
            setUserRegister.saveUser(user)
            _userRegisterSuccess.postValue(user)

            if(_userRegisterSuccess != null)
                SaveUserBySharePreferences.saveUserBySharePreferences(Keys.PREF_USER, Gson().toJson(user))
        }
    }

}