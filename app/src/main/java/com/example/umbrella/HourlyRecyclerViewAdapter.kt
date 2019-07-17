package com.example.umbrella

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.umbrella.R.layout.hourly_recyclerview_items
import com.example.umbrella.model.datasource.openweather.hourlyweather.X
import kotlinx.android.synthetic.main.hourly_recyclerview_items.view.*

class HourlyRecyclerViewAdapter(private var weatherDetails: List<X>?, private val context: Context, conversion: Int) :
    RecyclerView.Adapter<ViewHolder>() {

    private var tempChoice = conversion
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(hourly_recyclerview_items, parent, false))
    }

    override fun getItemCount(): Int {
        return weatherDetails!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val temp = weatherDetails!![position].main.temp.toString()

        //Check for radio button checked
        //Fahrenheit chosen
        if (tempChoice == R.id.rbFahrenheit) {
            //convert to Fahrenheit
            val convertedTemp = convertToFahrenheit(temp.toDouble())
            holder.itemView.tvHourTemp.text = "Temperature: $convertedTemp°F"

        }else if (tempChoice == R.id.rbCelsius) {
            val convertedTemp = convertToCelsius(temp.toDouble())
            holder.itemView.tvHourTemp.text = "Temperature: $convertedTemp°C"
        }
        holder.itemView.tvDate.text = "Date: ${weatherDetails?.get(position)?.dtTxt}"
        holder.itemView.tvDesc.text = weatherDetails?.get(position)?.weather?.get(0)?.description

    }
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
}
