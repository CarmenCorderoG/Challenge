package com.example.mychallenge.domain

import com.example.mychallenge.data.repository.TopTensRepository
import com.example.mychallenge.domain.module.TopTens
import javax.inject.Inject

class GetTopTens @Inject constructor(private val repositoryTopTens: TopTensRepository) {

    suspend operator fun invoke(): List<TopTens> = repositoryTopTens.getTopTensList()

}