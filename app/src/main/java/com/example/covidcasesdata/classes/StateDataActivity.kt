package com.example.covidcasesdata.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covidcasesdata.R
import com.example.covidcasesdata.databinding.ActivityMainBinding
import com.example.covidcasesdata.databinding.ActivityStateData2Binding
import com.example.covidcasesdata.models.StateTotal
import com.example.covidcasesdata.utils.HelperUtil.Companion.convertToINS

class StateDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateData2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateData2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    private fun init(){
        if(intent==null) return
        val stateTotal: StateTotal = intent.getSerializableExtra("StateData") as StateTotal
        binding.tvStateName.text = stateTotal.state
        binding.tvTotalStateActive.text = "Active: "+ convertToINS(stateTotal.active)
        binding.tvTotalStateConfirmed.text = "Confirmed: "+ convertToINS(stateTotal.confirmed)
        binding.tvTotalStateDeceased.text = "Deaths: " + convertToINS(stateTotal.deaths)
        binding.tvTotalStateRecovered.text = "Recovered: "+ convertToINS(stateTotal.recovered)
    }
}