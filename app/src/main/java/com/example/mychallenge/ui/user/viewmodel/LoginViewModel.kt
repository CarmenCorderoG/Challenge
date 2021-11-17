package com.example.mychallenge.ui.user.viewmodel

import androidx.lifecycle.*
import com.example.mychallenge.common.Extensions.isEmailValid
import com.example.mychallenge.common.Extensions.isEqualPassword
import com.example.mychallenge.common.Extensions.isPasswordValid
import com.example.mychallenge.common.Keys.Companion.PREF_USER
import com.example.mychallenge.common.StatusUser
import com.example.mychallenge.domain.*
import com.example.mychallenge.domain.module.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
        private val getValidUser: GetValidUserCredentials,
        private val saveUserBySharePreferences: SaveUserBySharePreferences,
        private val getUserBySharePreferences: GetUserBySharePreferences,
        private val deleteUser: DeleteUser,
        private val getCanUseBiometric: GetCanAuthenticateByBiometric): ViewModel() {

    private val _validInfoMsg = MutableLiveData<StatusUser>()
    val validInfoMsg: LiveData<StatusUser> get() = _validInfoMsg

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _userSharePreferences = MutableLiveData<User>()
    val userSharePreferences: LiveData<User> get() = _userSharePreferences

    private val _notUserInDevice = MutableLiveData<Boolean>()
    val notUserInDevice: LiveData<Boolean> get() = _notUserInDevice

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _biometricEnable = MutableLiveData<Boolean>()
    val biometricEnable: LiveData<Boolean> get() = _biometricEnable

    private val _executor = MutableLiveData<Executor>()
    val executor: LiveData<Executor> get() = _executor


    init {
        _loading.postValue(false)
        getStartUser()
    }

    fun getStartUser() {
        val jsonUser = Gson().fromJson(
            getUserBySharePreferences.getUserSharePreferences(PREF_USER),
            User::class.java
        )
        _notUserInDevice.postValue(jsonUser == null)
        if (jsonUser != null) {
            jsonUser.id
            _biometricEnable.postValue(getCanUseBiometric.getUserUseBiometric())
            _executor.postValue(getCanUseBiometric.getExecutorReady())
            _userSharePreferences.postValue(jsonUser)
        }
    }

    fun getChangeMethodLogin(): LiveData<Boolean> {
        _biometricEnable.value = _biometricEnable.value?.not() ?: !_biometricEnable.value!!
        return biometricEnable
    }


    fun cleanSharePreferences() {
        deleteUser.deleteUser()
        _notUserInDevice.postValue(true)
    }

    fun getLoginUser(mail: String, password: String) {
        _loading.postValue(true)
        viewModelScope.launch {
            getValidUser.getUserInfo(mail, password).collect { oldUser ->
                _user.postValue(oldUser)

                if (_user != null)
                    saveUserBySharePreferences.saveUserBySharePreferences(
                        PREF_USER,
                        Gson().toJson(oldUser)
                    )

                _loading.postValue(false)
            }
        }
    }

    fun validInfoLoginUser(mail: String, password: String) {
        _loading.postValue(true)
        if (userSharePreferences.value != null) {
            _validInfoMsg.value = when {
                password.isPasswordValid() == StatusUser.PASSWORD_ERROR -> password.isPasswordValid()

                password.isEqualPassword(userSharePreferences.value!!.password!!) ==
                        StatusUser.PASSWORD_NOT_THE_SAME -> StatusUser.PASSWORD_NOT_THE_SAME_BY_USER_SAVE

                else -> StatusUser.INFO_SUCCESS_BY_USER_SAVE
            }

        } else {
            _validInfoMsg.value = when {
                mail.isEmailValid() == StatusUser.MAIL_ERROR -> mail.isEmailValid()
                password.isPasswordValid() == StatusUser.PASSWORD_ERROR -> password.isPasswordValid()
                else -> StatusUser.INFO_SUCCESS
            }
        }

        _loading.postValue(false)
    }

}