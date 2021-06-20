package com.example.covidcasesdata.classes

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.adapters.IndiaDayWiseAdapter
import com.example.covidcasesdata.databinding.ActivityMainBinding
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.viewmodel.MyViewModel
import com.example.covidcasesdata.utils.HelperUtil
import com.example.covidcasesdata.utils.HelperUtil.Companion.convertToINS


class MainActivity : AppCompatActivity() {
    lateinit var myViewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private var indiaDataDayWiseList = ArrayList<IndiaPerDay>()
    private lateinit var indiaDayWiseAdapter: IndiaDayWiseAdapter
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

    private fun isInternetAvailable() {
//        try {
//            val ipAddr: InetAddress = InetAddress.getByName("google.com")
//            //You can replace it with your name
//            if(ipAddr.equals("")) return
//        }catch (e: Exception){
//            var alertDialog: AlertDialog = AlertDialog.Builder(this).create()
//            alertDialog.setMessage("No internet Connection.")
//            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, this,
//                    )
//        }
    }

    private fun observeIndiaDayWiseData() {
        //what is owner here? why? for life cycle?
        myViewModel.mutableLiveDataList().observe(this, {
            indiaDataDayWiseList = it as ArrayList<IndiaPerDay>
            fillTotalIndiaData(indiaDataDayWiseList[indiaDataDayWiseList.size - 1])
            indiaDayWiseAdapter.updateDailyData(indiaDataDayWiseList)
        })
    }

    private fun fillTotalIndiaData(indiaPerDay: IndiaPerDay) {
        binding.tvTotalIndiaConfirmed.text = "Confirmed: " + convertToINS(indiaPerDay.totalConfirmed)
        binding.tvTotalIndiaDeceased.text = "Deceased: " + convertToINS(indiaPerDay.totalDeceased)
        binding.tvTotalIndiaRecovered.text = "Recovered: " + convertToINS(indiaPerDay.totalRecovered)
        binding.tvTotalIndiaActive.text = "Active: " + convertToINS((indiaPerDay.totalConfirmed.toInt()
                - indiaPerDay.totalDeceased.toInt()
                - indiaPerDay.totalRecovered.toInt()).toString())
    }

    private fun init() {
        indiaDayWiseAdapter = IndiaDayWiseAdapter(indiaDataDayWiseList)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        binding.recyclerView.adapter = indiaDayWiseAdapter
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            linearLayoutManager.orientation
        )
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        myViewModel.loadIndiaDayWiseData()
    }
}