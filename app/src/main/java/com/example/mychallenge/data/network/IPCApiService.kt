package com.example.mychallenge.data.network

import com.example.mychallenge.domain.module.IPCData
import retrofit2.http.GET

interface IPCApiService {
    @GET("cc4c350b-1f11-42a0-a1aa-f8593eafeb1e")
    suspend fun getPriceAndMarketRates(): List<IPCData>
}