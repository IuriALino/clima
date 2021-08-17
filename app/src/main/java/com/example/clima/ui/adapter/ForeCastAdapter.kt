package com.example.clima.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clima.data.source.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS
import com.example.clima.data.source.formatToPattern
import com.example.clima.data.source.parseToDate
import com.example.clima.data.source.retrofit.response.WeatherResponse
import com.example.clima.databinding.RowItemForecastBinding

class ForeCastAdapter : RecyclerView.Adapter<ForeCastAdapter.ViewHolder>()
{
    var forecast = listOf<WeatherResponse>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowItemForecastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //Representation Layout
    inner class ViewHolder(private val binding: RowItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeatherResponse) = with(binding) {
            textViewDate.text = item.dtTxt.parseToDate(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)?.formatToPattern()
            textViewForecastTemperature.text = item.main.temp.toString()
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecast[position])
    }
    override fun getItemCount(): Int {
        return forecast.size
    }
}