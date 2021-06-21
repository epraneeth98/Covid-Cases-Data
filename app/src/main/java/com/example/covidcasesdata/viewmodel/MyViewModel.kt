package com.example.covidcasesdata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidcasesdata.models.Covid19IndiaData
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
    private val statesTotalCasesListLiveData: MutableLiveData<HashMap<String, StateTotal>> =
        MutableLiveData<HashMap<String, StateTotal>>()
    private val statesListLiveData: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    fun indiaCasesLiveDataList() = dailyIndiaCasesListLiveData
    fun statesCasesLiveDataList() = statesTotalCasesListLiveData
    fun statesNamesLiveDataList() = statesListLiveData
    fun loadIndiaDayWiseData() {
        CoroutineScope(Dispatchers.IO).launch {
            val covid19IndiaData: Covid19IndiaData = myRepository.getCovidIndiaAndStatesData()
            val dayWiseIndiaData: List<IndiaPerDay> = covid19IndiaData.indiaPerDayList
            val statesTotalData: List<StateTotal> = covid19IndiaData.statesTotalList
            val statesList: ArrayList<String> = ArrayList()
            var stateNameStateTotalHashMap: HashMap<String, StateTotal> = HashMap()
            for(stateTotal in statesTotalData){
                statesList.add(stateTotal.state)
                stateNameStateTotalHashMap[stateTotal.state] = stateTotal
            }
            dailyIndiaCasesListLiveData.postValue(dayWiseIndiaData)
            statesTotalCasesListLiveData.postValue(stateNameStateTotalHashMap)
            statesListLiveData.postValue(statesList)
        }
    }

}