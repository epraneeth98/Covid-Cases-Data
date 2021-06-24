package com.example.covidcasesdata.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.adapters.IndiaDayWiseAdapter.DayWiseCasesViewHolder
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.models.StateTillADay
import com.example.covidcasesdata.models.StatesTillADayList
import com.example.covidcasesdata.utils.HelperUtil.Companion.YMDtoDayMonth
import com.example.covidcasesdata.utils.HelperUtil.Companion.convertToINS

class StatesDayWiseAdapter(
    private var statesTillADayList: List<StatesTillADayList>,
    private val stateName: String
) :
    RecyclerView.Adapter<DayWiseCasesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayWiseCasesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_daywise_cases, parent, false)
        return DayWiseCasesViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DayWiseCasesViewHolder,
        position: Int
    ) {
        if (position == statesTillADayList.size - 1) return
        val statesPerDay: StatesTillADayList = statesTillADayList[position]
        val statesPerDayPreviousDay: StatesTillADayList = statesTillADayList[position + 1]
        val stateTillADay: StateTillADay = getRequiredStateTillADay(stateName, statesPerDay)
        val stateTillPreviousDay: StateTillADay =
            getRequiredStateTillADay(stateName, statesPerDayPreviousDay)

        holder.tvDate.text = YMDtoDayMonth(statesPerDay.day)
        holder.tvConfirmed.text =
            "Confirmed: " + convertToINS((stateTillADay.totalConfirmed.toInt() - stateTillPreviousDay.totalConfirmed.toInt()).toString())
        holder.tvDeceased.text =
            "Deaths:       " + convertToINS((stateTillADay.deaths.toInt() - stateTillPreviousDay.deaths.toInt()).toString())
        holder.tvRecovered.text =
            "Recovered: " + convertToINS((stateTillADay.discharged.toInt() - stateTillPreviousDay.discharged.toInt()).toString())

        if(holder.tvDate.text == "Yesterday" || holder.tvDate.text == "Today") holder.tvConfirmed.setTypeface(holder.tvConfirmed.typeface, Typeface.BOLD)
    }

    fun getRequiredStateTillADay(stateCompleteName: String, statesPerDay: StatesTillADayList)
            : StateTillADay {
        for (stateTillADay in statesPerDay.statesTotalListTillADay) {
            if (stateCompleteName == stateTillADay.stateName) return stateTillADay
        }
        return statesPerDay.statesTotalListTillADay[0]
    }

    override fun getItemCount(): Int {
        return statesTillADayList.size
    }

    fun updateDailyData(statesTillADayList: List<StatesTillADayList>) {
        this.statesTillADayList = statesTillADayList.reversed()
        notifyDataSetChanged()
    }
}