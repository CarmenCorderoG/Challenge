package com.example.mychallenge.data.repository

import com.example.mychallenge.data.network.TopTensApiService
import com.example.mychallenge.domain.module.TopTens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopTensRepository @Inject constructor(
    private val topTensApiService: TopTensApiService) {

    suspend fun getTopTensList(): List<TopTens> {
        return withContext(Dispatchers.IO){
            topTensApiService.getTopTens()
        }
    }

}