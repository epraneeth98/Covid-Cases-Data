package com.example.covidcasesdata.classes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
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
//        getCSVdata()
    }

    private fun getCSVdata() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://sample-videos.com/csv/Sample-Spreadsheet-10-rows.csv"

        val stringRequest = StringRequest(
                Request.Method.GET, url,
            { response ->

                    // Display the first 500 characters of the response string.
//                binding.csvData.text = "Response is: ${response.substring(0, 500)}"
//                    val lines = response.substring(0)
//                Log.d("abc", ""+lines)
//                var values: List<String> = lines.split("\n")
//                Log.d("abc", "values size: "+values.size)

            },
            { binding.csvData.text = "That didn't work!"
                Log.d("abc", "That didn't work!")})

        queue.add(stringRequest)
        queue.start()
    }

    private fun init() {
        if (intent == null) return
        val stateTotal: StateTotal = intent.getSerializableExtra("StateData") as StateTotal
        binding.tvStateName.text = stateTotal.state
        binding.tvTotalStateActive.text = "Active: " + convertToINS(stateTotal.active)
        binding.tvTotalStateConfirmed.text = "Confirmed: " + convertToINS(stateTotal.confirmed)
        binding.tvTotalStateDeceased.text = "Deaths: " + convertToINS(stateTotal.deaths)
        binding.tvTotalStateRecovered.text = "Recovered: " + convertToINS(stateTotal.recovered)
    }
}