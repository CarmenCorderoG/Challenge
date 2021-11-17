package com.example.mychallenge.data.repository

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.FragmentScoped
import java.util.concurrent.Executor
import javax.inject.Inject

class BiometricRepository @Inject constructor(
    private val biometricAuthenticate: Int,
    private val executor: Executor) {

    fun getUserCanAuthenticateByBiometric(): Boolean = biometricAuthenticate == BiometricManager.BIOMETRIC_SUCCESS

    fun getExecutor(): Executor = executor
}