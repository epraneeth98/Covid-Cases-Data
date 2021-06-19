package com.example.covidcasesdata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.repository.MyRepository
import com.example.covidcasesdata.repository.retrofit.RetrofitAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel: ViewModel() {

    private var myRepository: MyRepository
    private val retrofitAPIService: RetrofitAPIService = RetrofitAPIService.getInstance()

    init {
        myRepository = MyRepository(retrofitAPIService)
    }
    // try  = MutableLiveData() later
    private val dailyIndiaCasesMutableLivePer: MutableLiveData<List<IndiaPerDay>> = MutableLiveData<List<IndiaPerDay>>()

    fun mutableLiveDataList() = dailyIndiaCasesMutableLivePer
    fun loadIndiaDataDayWise() {
        CoroutineScope(Dispatchers.IO).launch {
            var dayWiseIndiaData = myRepository.getDailyWiseIndiaData().indiaPerDay
            dailyIndiaCasesMutableLivePer.postValue(dayWiseIndiaData)
        }
    }

}