package com.example.covidcasesdata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.models.StateTotal
import com.example.covidcasesdata.repository.MyRepository
import com.example.covidcasesdata.repository.retrofit.RetrofitAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private var myRepository: MyRepository
    private val retrofitAPIService: RetrofitAPIService = RetrofitAPIService.getInstance()

    init {
        myRepository = MyRepository(retrofitAPIService)
    }

    // try  = MutableLiveData() later
    private val dailyIndiaCasesListLiveData: MutableLiveData<List<IndiaPerDay>> =
        MutableLiveData<List<IndiaPerDay>>()
    private val statesTotalCasesListLiveData: MutableLiveData<List<StateTotal>> =
        MutableLiveData<List<StateTotal>>()

    fun mutableLiveDataList() = dailyIndiaCasesListLiveData
    fun loadIndiaDayWiseData() {
        CoroutineScope(Dispatchers.IO).launch {
            val dayWiseIndiaData = myRepository.getCovidIndiaAndStatesData().indiaPerDayList
            val statesTotalData = myRepository.getCovidIndiaAndStatesData().statesTotalList
            dailyIndiaCasesListLiveData.postValue(dayWiseIndiaData)
            statesTotalCasesListLiveData.postValue(statesTotalData)
        }
    }

}