package com.example.covidcasesdata.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.adapters.DayWiseCasesIndiaAdapter
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.viewmodel.MyViewModel

class MainActivity : AppCompatActivity() {
    lateinit var myViewModel: MyViewModel
    private var indiaDataDayWiseList = ArrayList<IndiaPerDay>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var dayWiseCasesIndiaAdapter: DayWiseCasesIndiaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init()
        observeIndiaDayWiseData()
    }

    private fun observeIndiaDayWiseData() {
        //what is owner here? why? for life cycle?
        myViewModel.mutableLiveDataList().observe(this, {
            indiaDataDayWiseList = it as ArrayList<IndiaPerDay>
            dayWiseCasesIndiaAdapter.updateDailyData(indiaDataDayWiseList)
        })
    }

    private fun init() {
        dayWiseCasesIndiaAdapter = DayWiseCasesIndiaAdapter(indiaDataDayWiseList)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        recyclerView = findViewById(R.id.recycler_view_daywise_cases_India)
        recyclerView.adapter = dayWiseCasesIndiaAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        myViewModel.loadIndiaDataDayWise()
    }
}