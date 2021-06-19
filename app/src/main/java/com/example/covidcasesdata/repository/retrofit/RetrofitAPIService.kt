package com.example.covidcasesdata.repository.retrofit

import com.example.covidcasesdata.utils.EndPoint
import retrofit2.http.*

import com.example.covidcasesdata.models.Covid19IndiaData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RetrofitAPIService {
    // check writing DailyDataIndia
    @GET("data.json")
    suspend fun getCovidIndiaAndStatesData(): Covid19IndiaData

    companion object {
        var retrofitService: RetrofitAPIService? = null
        fun getInstance(): RetrofitAPIService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(EndPoint.INDIA_DAYWISE_DATA_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitAPIService::class.java)
            }
            return retrofitService!!
        }
    }
}