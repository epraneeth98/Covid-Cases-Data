package com.example.covidcasesdata.repository

import com.example.covidcasesdata.repository.retrofit.RetrofitAPIService

class MyRepository(private val retrofitAPIService: RetrofitAPIService) {
    suspend fun getCovidIndiaAndStatesData() = retrofitAPIService.getCovidIndiaAndStatesData()

}