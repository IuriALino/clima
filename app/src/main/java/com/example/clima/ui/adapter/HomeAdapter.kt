package com.example.clima.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clima.data.Model
import com.example.clima.data.source.kelvinToCelsius
import com.example.clima.databinding.RowItemBinding

class HomeAdapter(
    private val cities : List<Model>,
    private val onItemSelected: (item: Model) -> Unit = { }
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //Representation Layout
    inner class ViewHolder(private val binding: RowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Model) = with(binding) {
            textViewCity.text = item.cityEnum.description
            textViewTemperature.text = item.temperature.kelvinToCelsius()
            cvHome.setOnClickListener {
                onItemSelected(item)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }
}