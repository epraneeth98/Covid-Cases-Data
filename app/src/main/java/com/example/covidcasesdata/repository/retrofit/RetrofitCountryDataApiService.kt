package com.example.covidcasesdata.repository.retrofit

import com.example.covidcasesdata.utils.EndPoint
import retrofit2.http.*

import com.example.covidcasesdata.models.Covid19IndiaData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitCountryDataApiService {
    // check writing DailyDataIndia
    @GET("data.json")
    suspend fun getCovidIndiaAndStatesData(): Covid19IndiaData

    companion object {
        var retrofitCountryDataService: RetrofitCountryDataApiService? = null
        fun getInstance(): RetrofitCountryDataApiService {

            if (retrofitCountryDataService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(EndPoint.INDIA_DAYWISE_DATA_END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitCountryDataService = retrofit.create(RetrofitCountryDataApiService::class.java)
            }
            return retrofitCountryDataService!!
        }
    }
}