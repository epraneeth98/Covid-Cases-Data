package com.example.covidcasesdata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.models.IndiaPerDay
import com.example.covidcasesdata.utils.HelperUtil.Companion.YMDtoDayMonth

class IndiaDayWiseAdapter(private var indiaPerDay: List<IndiaPerDay>) :
    RecyclerView.Adapter<IndiaDayWiseAdapter.DayWiseCasesIndiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWiseCasesIndiaViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_daywise_indiacases, parent, false)
        return DayWiseCasesIndiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayWiseCasesIndiaViewHolder, position: Int) {
        val indiaPerDay: IndiaPerDay = indiaPerDay[position]
        holder.tvDate.text = YMDtoDayMonth(indiaPerDay.date)
        holder.tvConfirmed.text = indiaPerDay.dailyConfirmed
        holder.tvDeceased.text = indiaPerDay.dailyDeceased
        holder.tvRecovered.text = indiaPerDay.dailyRecovered
    }

    override fun getItemCount(): Int {
        return indiaPerDay.size
    }

    fun updateDailyData(indiaPerDay: List<IndiaPerDay>) {
        this.indiaPerDay = indiaPerDay.reversed()
        notifyDataSetChanged()
    }


    class DayWiseCasesIndiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        val tvConfirmed: TextView = itemView.findViewById(R.id.tv_confirmed_no)
        val tvDeceased: TextView = itemView.findViewById(R.id.tv_deceased_no)
        val tvRecovered: TextView = itemView.findViewById(R.id.tv_recovered_no)
    }
}