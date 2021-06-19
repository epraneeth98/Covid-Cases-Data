package com.example.covidcasesdata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidcasesdata.R
import com.example.covidcasesdata.models.IndiaPerDay

class DayWiseCasesIndiaAdapter (private var indiaPerDay: List<IndiaPerDay>):
    RecyclerView.Adapter<DayWiseCasesIndiaAdapter.DayWiseCasesIndiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWiseCasesIndiaViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_daywise_indiacases, parent, false)
        return DayWiseCasesIndiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayWiseCasesIndiaViewHolder, position: Int) {
        val indiaPerInADay: IndiaPerDay = indiaPerDay[position]
        holder.tvDate.text = indiaPerInADay.date
        holder.tvConfirmed.text = indiaPerInADay.dailyConfirmed
        holder.tvDeceased.text = indiaPerInADay.dailyDeceased
        holder.tvRecovered.text = indiaPerInADay.dailyRecovered
    }

    override fun getItemCount(): Int {
        return indiaPerDay.size
    }

    fun updateDailyData(indiaPerDay: List<IndiaPerDay>){
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