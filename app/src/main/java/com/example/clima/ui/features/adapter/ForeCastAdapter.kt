package com.example.clima.ui.features.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clima.data.model.ForeCastModel
import com.example.clima.databinding.RowItemForecastBinding
import com.example.clima.domain.model.ForeCastDomain

class ForeCastAdapter : RecyclerView.Adapter<ForeCastAdapter.ViewHolder>()
{
    var forecast = listOf<ForeCastDomain>()
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
        fun bind(item: ForeCastDomain) = with(binding) {
            textViewDate.text = item.date
            textViewForecastTemperature.text = item.temperature
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecast[position])
    }
    override fun getItemCount(): Int {
        return forecast.size
    }
}