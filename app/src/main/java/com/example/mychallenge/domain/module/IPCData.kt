package com.example.mychallenge.domain.module

import com.google.gson.annotations.SerializedName
import java.util.*

data class IPCData(
    @SerializedName("date")
    var date: String?,

    @SerializedName("price")
    var price: Float?,

    @SerializedName("percentageChange")
    var percentageChange: Double?,

    @SerializedName("volume")
    var volume: Int?,

    @SerializedName("change")
    var change: Double?)