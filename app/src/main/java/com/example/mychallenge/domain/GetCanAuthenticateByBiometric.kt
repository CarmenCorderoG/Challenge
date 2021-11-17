package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.BiometricRepository
import java.util.concurrent.Executor
import javax.inject.Inject

class GetCanAuthenticateByBiometric @Inject constructor(
    private val biometricRepository: BiometricRepository) {

    fun getUserUseBiometric(): Boolean = biometricRepository.getUserCanAuthenticateByBiometric()
    fun getExecutorReady(): Executor = biometricRepository.getExecutor()

}