package com.example.covidcasesdata.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.covidcasesdata.MyApplication
import com.example.covidcasesdata.models.*
import com.example.covidcasesdata.repository.MyRepository
import com.example.covidcasesdata.repository.retrofit.RetrofitCountryDataApiService
import com.example.covidcasesdata.repository.retrofit.RetrofitStateDataApiService
import com.example.covidcasesdata.utils.AppConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private var myRepository: MyRepository
    private val retrofitCountryDataApiService: RetrofitCountryDataApiService =
        RetrofitCountryDataApiService.getInstance()
    private val retrofitStateDataApiService: RetrofitStateDataApiService =
        RetrofitStateDataApiService.getInstance()

    init {
        myRepository = MyRepository(retrofitCountryDataApiService, retrofitStateDataApiService)
    }

    // IndiaData
    private val dailyIndiaCasesListLiveData: MutableLiveData<List<IndiaPerDay>> =
        MutableLiveData<List<IndiaPerDay>>()
    private val statesTotalCasesListLiveData: MutableLiveData<HashMap<String, StateTotal>> =
        MutableLiveData<HashMap<String, StateTotal>>()
    private val statesListLiveData: MutableLiveData<List<String>> = MutableLiveData<List<String>>()

    //StatesData
    private val statesEveryDayTillADayListLiveData: MutableLiveData<List<StatesTillADayList>> =
        MutableLiveData<List<StatesTillADayList>>()


    fun indiaCasesLiveDataList() = dailyIndiaCasesListLiveData
    fun statesCasesLiveDataList() = statesTotalCasesListLiveData
    fun statesNamesLiveDataList() = statesListLiveData
    fun statesEveryDayTillADayList() = statesEveryDayTillADayListLiveData
    fun loadIndiaDayWiseData() {
        CoroutineScope(Dispatchers.IO).launch {
            val covid19IndiaData: Covid19IndiaData = myRepository.getCovidIndiaAndStatesData()
            val dayWiseIndiaData: List<IndiaPerDay> = covid19IndiaData.indiaPerDayList
            val statesTotalData: List<StateTotal> = covid19IndiaData.statesTotalList
            val statesList: ArrayList<String> = ArrayList()
            var stateNameStateTotalHashMap: HashMap<String, StateTotal> = HashMap()
            for (stateTotal in statesTotalData) {
                statesList.add(stateTotal.state)
                stateNameStateTotalHashMap[stateTotal.state] = stateTotal
            }
            dailyIndiaCasesListLiveData.postValue(dayWiseIndiaData)
            statesTotalCasesListLiveData.postValue(stateNameStateTotalHashMap)
            statesListLiveData.postValue(statesList)
        }
    }

    fun loadStatesDayWiseData() {
        CoroutineScope(Dispatchers.IO).launch {
            val covid19StatesData: Covid19StatesData = myRepository.getCovidStatesData()
            val dayWiseAllStatesData: List<StatesTillADayList> = covid19StatesData.everyDayListOfStatesTillADay
            statesEveryDayTillADayListLiveData.postValue(dayWiseAllStatesData)
        }
    }

    fun getSearchedStateSharedPreference(key: String): String? {
        return MyApplication.sharedPreferences.getString(key, AppConstants.NO_RECENT_SEARCH)
    }

    fun updateSearchedStateSharedPreference(key: String, value: String){
        with(MyApplication.sharedPreferences.edit()){
            putString(key, value)
            apply()
        }
    }

    fun clearSharedPreferences(){
        with(MyApplication.sharedPreferences.edit()){
            clear()
            apply()
        }
    }

}