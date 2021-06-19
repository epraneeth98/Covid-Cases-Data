package com.example.covidcasesdata.models

import com.google.gson.annotations.SerializedName

data class IndiaDayWise (
    @SerializedName("cases_time_series")
    val indiaPerDay: List<IndiaPerDay>
)

data class IndiaPerDay(
    @SerializedName("dailyconfirmed")
    val dailyConfirmed: String,

    @SerializedName("dailydeceased")
    val dailyDeceased: String,

    @SerializedName("dailyrecovered")
    val dailyRecovered: String,

    @SerializedName("dateymd")
    val date: String,

    @SerializedName("totalconfirmed")
    val totalConfirmed: String,

    @SerializedName("totaldeceased")
    val totalDeceased: String,

    @SerializedName("totalrecovered")
    val totalRecovered: String

)