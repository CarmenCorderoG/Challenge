package com.example.mychallenge.data.repository

import com.example.mychallenge.data.network.IPCApiService
import com.example.mychallenge.domain.module.IPCData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IPCRepository @Inject constructor(private val ipcApiService: IPCApiService) {

    suspend fun getIPCList(): List<IPCData>{
        return withContext(Dispatchers.IO){
            ipcApiService.getPriceAndMarketRates()
        }
    }


}