package com.example.covidcasesdata.models

import com.google.gson.annotations.SerializedName

data class Covid19IndiaData (
    @SerializedName("cases_time_series")
    val indiaPerDayList: List<IndiaPerDay>,
    @SerializedName("statewise")
    val statesTotalList: List<StateTotal>
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

data class StateTotal(
    val active: String,
    val confirmed: String,
    val deaths: String,
    val recovered: String,
    val state: String,
    @SerializedName("statecode")
    val stateCode: String
)