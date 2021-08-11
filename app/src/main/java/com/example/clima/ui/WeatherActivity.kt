package com.example.clima.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clima.R
import com.example.clima.data.CityEnum
import com.example.clima.data.Model
import com.example.clima.databinding.ActivityWeatherBinding
import com.example.clima.ui.adapter.HomeAdapter
import com.example.clima.ui.common.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherActivity : BaseActivity<ActivityWeatherBinding>() {

    private val weatherViewModel by viewModel<WeatherViewModel>()

    val cities = listOf(Model(CityEnum.MELBOURNE), Model(CityEnum.MONTE_CARLO), Model(CityEnum.SAO_PAULO), Model(CityEnum.SILVERSTONE))

    override fun initUIComponents() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@WeatherActivity)
            adapter = _adapter
        }
    }

    private val _adapter by lazy {
        HomeAdapter(cities) {
            startActivity(ForecastActivity.newIntent(this,"${it.cityEnum.description}, ${it.cityEnum.country}"))
        }
    }

    override fun initUIEvents() {
        updateWeather()
    }

    private fun updateWeather() {
        cities.forEach {
            weatherViewModel.requestWeather(it.cityEnum)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_update, menu)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val item = menu?.findItem(R.id.action_search)
//        item?.let {
//            val searchView = it.actionView as SearchView
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//            searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
//                androidx.appcompat.widget.SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    return false
//                }
//                override fun onQueryTextChange(newText: String): Boolean {
//                    return false
//                }
//            })
//        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_update -> {
                updateWeather()
                true
            }
//            R.id.action_search -> {
//                // Verify the action and get the query
//                true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun subscribeUI() {
        weatherViewModel.openWeatherResponse.observe(this){
            it?.let { weather ->
                cities.forEach { model ->
                    if(weather.name .equals(model.cityEnum.description, true)  ){
                        model.temperature = weather.main.temp
                    }
                }
                _adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getViewBinding() = ActivityWeatherBinding.inflate(layoutInflater)
}