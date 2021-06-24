package com.example.covidcasesdata.repository.retrofit

import com.example.covidcasesdata.models.Covid19StatesData
import com.example.covidcasesdata.utils.EndPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitStateDataApiService {
    @GET("history")
    suspend fun getCovidStatesData(): Covid19StatesData

    companion object {
        var retrofitStateDataApiService: RetrofitStateDataApiService? = null
        fun getInstance(): RetrofitStateDataApiService {
            if (retrofitStateDataApiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(EndPoint.STATES_TILL_A_DAY_DATA_END_POINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitStateDataApiService =
                    retrofit.create(RetrofitStateDataApiService::class.java)
            }
            return retrofitStateDataApiService!!
        }
    }
}