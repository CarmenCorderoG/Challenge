package com.example.mychallenge.di

import android.app.Application
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.concurrent.Executor

@Module
@InstallIn(ViewModelComponent::class)
object BiometricModule {

    @Provides
    fun providerBiometric(app: Application):Int {
        val biometricManager = BiometricManager.from(app)
        return biometricManager.canAuthenticate()
    }

    @Provides
    fun providerExecutor(app: Application): Executor = ContextCompat.getMainExecutor(app)

}