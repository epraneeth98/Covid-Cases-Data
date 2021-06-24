package com.example.covidcasesdata.models

import com.google.gson.annotations.SerializedName

data class Covid19StatesData(
    @SerializedName("data")
    val everyDayListOfStatesTillADay: List<StatesTillADayList>
)
data class StatesTillADayList(
    val day: String,
    @SerializedName("regional")
    val statesTotalListTillADay: List<StateTillADay>
)

data class StateTillADay(
    @SerializedName("loc")
    val stateName: String,
    val totalConfirmed: String,
    val discharged: String,
    val deaths: String,
)