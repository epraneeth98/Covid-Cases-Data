package com.example.covidcasesdata.classes

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.adapters.IndiaDayWiseAdapter
import com.example.covidcasesdata.databinding.ActivityMainBinding
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.models.StateTotal
import com.example.covidcasesdata.utils.HelperUtil.Companion.convertToINS
import com.example.covidcasesdata.viewmodel.MyViewModel


class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    lateinit var myViewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private var indiaDataDayWiseList = ArrayList<IndiaPerDay>()
    private lateinit var indiaDayWiseAdapter: IndiaDayWiseAdapter
    private var statesNames: ArrayList<String> = ArrayList()
    private var statesTotalCasesHashMap: HashMap<String, StateTotal> = HashMap()
    private var isStateListAvailable: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        init()
        isInternetAvailable()
        observeIndiaDayWiseData()
    }

    private fun setUpSearchBar() {
        ArrayAdapter(this, R.layout.simple_list_item_1, statesNames).also {
                adapter -> binding.searchBar.setAdapter(adapter)
        }
        binding.searchBar.onItemClickListener = this
    }

    private fun isInternetAvailable() {
    }

    private fun observeIndiaDayWiseData() {
        //what is owner here? why? for life cycle?
        myViewModel.indiaCasesLiveDataList().observe(this, {
            indiaDataDayWiseList = it as ArrayList<IndiaPerDay>
            fillTotalIndiaData(indiaDataDayWiseList[indiaDataDayWiseList.size - 1])
            indiaDayWiseAdapter.updateDailyData(indiaDataDayWiseList)
        })
        myViewModel.statesNamesLiveDataList().observe(this,{
            statesNames = it as ArrayList<String>
            isStateListAvailable = true
            setUpSearchBar()
        })
        myViewModel.statesCasesLiveDataList().observe(this, {
            statesTotalCasesHashMap = it as HashMap<String, StateTotal>
        })
    }

    @SuppressLint("SetTextI18n")
    private fun fillTotalIndiaData(indiaPerDay: IndiaPerDay) {
        binding.tvTotalIndiaConfirmed.text = "Confirmed: " + convertToINS(indiaPerDay.totalConfirmed)
        binding.tvTotalIndiaDeceased.text = "Deceased:  " + convertToINS(indiaPerDay.totalDeceased)
        binding.tvTotalIndiaRecovered.text = "Recovered: " + convertToINS(indiaPerDay.totalRecovered)
        binding.tvTotalIndiaActive.text = "Active:        " + convertToINS((indiaPerDay.totalConfirmed.toInt()
                - indiaPerDay.totalDeceased.toInt()
                - indiaPerDay.totalRecovered.toInt()).toString())
    }

    private fun init() {
        indiaDayWiseAdapter = IndiaDayWiseAdapter(indiaDataDayWiseList)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        binding.recyclerView.adapter = indiaDayWiseAdapter
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        myViewModel.loadIndiaDayWiseData()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val stateName: String = parent?.getItemAtPosition(position) as String
        binding.searchBar.text.clear()
        val intent = Intent(this,StateDataActivity::class.java)
        intent.putExtra("StateData", statesTotalCasesHashMap[stateName])
        startActivity(intent)
    }
}