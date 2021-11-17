package com.example.mychallenge.domain.module

import com.google.gson.annotations.SerializedName

data class TopTens(
    @SerializedName("issueId")
    var issueId: String?,

    @SerializedName("openPrice")
    var openPrice: Double?,

    @SerializedName("maxPrice")
    var maxPrice: Double?,

    @SerializedName("minPrice")
    var minPrice: Double?,

    @SerializedName("percentageChange")
    var percentageChange: Double?,

    @SerializedName("valueChange")
    var valueChange: Double?,

    @SerializedName("aggregatedVolume")
    var aggregatedVolume: Int?,

    @SerializedName("bidPrice")
    var bidPrice: Double?,

    @SerializedName("bidVolume")
    var bidVolume: Int?,

    @SerializedName("askPrice")
    var askPrice: Double?,

    @SerializedName("askVolume")
    var askVolume: Int?,

    @SerializedName("ipcParticipationRate")
    var ipcParticipationRate: Double?,

    @SerializedName("lastPrice")
    var lastPrice: Double?,

    @SerializedName("closePrice")
    var closePrice: Double?,

    @SerializedName("riseLowTypeId")
    var riseLowTypeId: Int?,

    @SerializedName("instrumentTypeId")
    var instrumentTypeId: Int?,

    @SerializedName("benchmarkId")
    var benchmarkId: Int?,

    @SerializedName("benchmarkPercentage")
    var benchmarkPercentage: Int?,
)