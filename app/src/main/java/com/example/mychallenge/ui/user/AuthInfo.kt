package com.example.mychallenge.ui.user

import android.content.res.Resources.ID_NULL
import androidx.annotation.StringRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.mychallenge.BR
import com.google.android.material.textfield.TextInputLayout

class AuthInfo: BaseObservable() {

    var userMail: String? = null
        @Bindable get() = field
        set(userMail) {
            field = userMail
            notifyPropertyChanged(BR.userMail)
        }

    var userName: String? = null
        @Bindable get() = field
        set(userName) {
            field = userName
            notifyPropertyChanged(BR.userName)
        }

    var userPassword: String? = null
        @Bindable get() = field
        set(userPassword) {
            field = userPassword
            notifyPropertyChanged(BR.userPassword)
        }

    var userPasswordAgain: String? = null
        @Bindable get() = field
        set(userPasswordAgain) {
            field = userPasswordAgain
            notifyPropertyChanged(BR.userPasswordAgain)
        }


    @BindingAdapter("error")
    internal fun TextInputLayout.setError(@StringRes errorRes: Int) {
        error = errorRes.takeUnless { it == ID_NULL }?.let { resources.getString(it) }
    }
}


