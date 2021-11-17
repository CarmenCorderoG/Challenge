package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.IPCRepository
import com.example.mychallenge.domain.module.IPCData
import javax.inject.Inject

class GetIPC @Inject constructor(private val repositoryIPC: IPCRepository) {

    suspend operator fun invoke(): List<IPCData> = repositoryIPC.getIPCList()

}