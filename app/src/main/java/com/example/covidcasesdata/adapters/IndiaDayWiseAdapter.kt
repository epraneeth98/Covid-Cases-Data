package com.example.covidcasesdata.adapters

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.utils.HelperUtil.Companion.YMDtoDayMonth
import com.example.covidcasesdata.utils.HelperUtil.Companion.convertToINS

class IndiaDayWiseAdapter(private var indiaPerDay: List<IndiaPerDay>) :
    RecyclerView.Adapter<IndiaDayWiseAdapter.DayWiseCasesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWiseCasesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_daywise_cases, parent, false)
        return DayWiseCasesViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayWiseCasesViewHolder, position: Int) {
        val indiaPerDay: IndiaPerDay = indiaPerDay[position]
        holder.tvDate.text = YMDtoDayMonth(indiaPerDay.date)
        holder.tvConfirmed.text = "Confirmed: "+convertToINS(indiaPerDay.dailyConfirmed)
        holder.tvDeceased.text = "Deaths:       "+convertToINS(indiaPerDay.dailyDeceased)
        holder.tvRecovered.text = "Recovered: "+convertToINS(indiaPerDay.dailyRecovered)

        if(holder.tvDate.text == "Yesterday" || holder.tvDate.text == "Today") holder.tvConfirmed.setTypeface(holder.tvConfirmed.typeface, Typeface.BOLD)

    }

    override fun getItemCount(): Int {
        return indiaPerDay.size
    }

    fun updateDailyData(indiaPerDay: List<IndiaPerDay>) {
        this.indiaPerDay = indiaPerDay.reversed()
        notifyDataSetChanged()
    }


    class DayWiseCasesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvConfirmed: TextView = itemView.findViewById(R.id.tv_confirmed_no)
        val tvDeceased: TextView = itemView.findViewById(R.id.tv_deceased_no)
        val tvRecovered: TextView = itemView.findViewById(R.id.tv_recovered_no)
    }
}