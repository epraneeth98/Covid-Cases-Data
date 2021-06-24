package com.example.covidcasesdata.repository

import com.example.covidcasesdata.repository.retrofit.RetrofitCountryDataApiService
import com.example.covidcasesdata.repository.retrofit.RetrofitStateDataApiService

class MyRepository(
    private val retrofitCountryDataApiService: RetrofitCountryDataApiService,
    private val retrofitStateDataApiService: RetrofitStateDataApiService
) {
    suspend fun getCovidIndiaAndStatesData() =
        retrofitCountryDataApiService.getCovidIndiaAndStatesData()

    suspend fun getCovidStatesData() = retrofitStateDataApiService.getCovidStatesData()

}