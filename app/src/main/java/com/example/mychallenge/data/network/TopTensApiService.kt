package com.example.mychallenge.data.network

import com.example.mychallenge.domain.module.TopTens
import retrofit2.http.GET

interface TopTensApiService {
    @GET("b4eb963c-4aee-4b60-a378-20cb5b00678f")
    suspend fun getTopTens(): List<TopTens>

}