package com.example.covidcasesdata.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.adapters.StatesDayWiseAdapter
import com.example.covidcasesdata.databinding.ActivityStateData2Binding
import com.example.covidcasesdata.models.StateTotal
import com.example.covidcasesdata.models.StatesTillADayList
import com.example.covidcasesdata.utils.AppConstants
import com.example.covidcasesdata.utils.HelperUtil.Companion.convertToINS
import com.example.covidcasesdata.viewmodel.MyViewModel

class StateDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateData2Binding
    private var statesTillADayList: List<StatesTillADayList> = ArrayList()
    private var statesDataDayWiseTillADayList = ArrayList<StatesTillADayList>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var statesDayWiseAdapter: StatesDayWiseAdapter
    lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateData2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        observeStateDayWiseData()
    }

    private fun observeStateDayWiseData() {
        myViewModel.statesEveryDayTillADayList().observe(this,
            {
                statesDataDayWiseTillADayList = it as ArrayList<StatesTillADayList>
                statesDayWiseAdapter.updateDailyData(statesDataDayWiseTillADayList)
            })
    }

    private fun init() {
        if (intent == null) return
        val stateTotal: StateTotal = intent.getSerializableExtra("StateData") as StateTotal
        binding.tvStateName.text = stateTotal.state
        binding.tvTotalStateActive.text = "Active:        " + convertToINS(stateTotal.active)
        binding.tvTotalStateConfirmed.text = "Confirmed: " + convertToINS(stateTotal.confirmed)
        binding.tvTotalStateDeceased.text = "Deaths:       " + convertToINS(stateTotal.deaths)
        binding.tvTotalStateRecovered.text = "Recovered: " + convertToINS(stateTotal.recovered)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        addSearchToSharedPreference(stateTotal.state)

        Toast.makeText(this, "Data is Loading... Please Wait...", Toast.LENGTH_SHORT).show()
        if(stateTotal.state=="Andhra Pradesh") binding.stateMap.setImageResource(R.drawable.andhrapradesh_map)
        else if(stateTotal.state=="Telangana") binding.stateMap.setImageResource(R.drawable.telangana_map)

        statesDayWiseAdapter = StatesDayWiseAdapter(statesTillADayList, stateTotal.state)
        binding.recyclerView.adapter = statesDayWiseAdapter
        linearLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recyclerView.layoutManager = linearLayoutManager
        myViewModel.loadStatesDayWiseData()
    }

    private fun addSearchToSharedPreference(stateName: String) {
        myViewModel.updateSearchedStateSharedPreference(AppConstants.RECENT_STATE_SEARCH, stateName)
    }
}