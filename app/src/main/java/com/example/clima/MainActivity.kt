package com.example.clima

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clima.data.CityEnum
import com.example.clima.data.Model
import com.example.clima.databinding.ActivityMainBinding
import com.example.clima.ui.BaseActivity
import com.example.clima.ui.WeatherViewModel
import com.example.clima.ui.adapter.MenuHomeAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val weatherViewModel by viewModel<WeatherViewModel>()

    val cities = listOf(Model(CityEnum.MELBOURNE), Model(CityEnum.MONTE_CARLO), Model(CityEnum.SAO_PAULO), Model(CityEnum.SILVERSTONE))

    override fun initUIComponents() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = _adapter
        }

    }

    private val _adapter by lazy {
        MenuHomeAdapter(cities) {

        }
    }

    override fun initUIEvents() {
        cities.forEach {
            weatherViewModel.requestWeather(it.cityEnum)
        }
    }

    override fun subscribeUI() {
        weatherViewModel.openWeatherResponse.observe(this){
            it?.let { weather ->
                cities.forEach { model ->
                    if(weather.name .equals(model.cityEnum.description, true)  ){
                        model.temperature = weather.main.temp.toString()
                    }
                }
                _adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
}