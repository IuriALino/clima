package com.example.clima.ui

import android.app.SearchManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clima.R
import com.example.clima.data.model.CityEnum
import com.example.clima.data.model.Model
import com.example.clima.data.source.snackBarIndefinite
import com.example.clima.databinding.ActivityWeatherBinding
import com.example.clima.ui.adapter.HomeAdapter
import com.example.clima.ui.common.BaseActivity
import com.example.clima.view.ProgressLoader
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf

@KoinApiExtension
class WeatherActivity : BaseActivity<ActivityWeatherBinding>() {

    private val weatherViewModel by viewModel<WeatherViewModel>()
    private val progressLoader by inject<ProgressLoader> { parametersOf(this) }

    val cities = listOf(
        Model(CityEnum.MELBOURNE), Model(CityEnum.MONTE_CARLO), Model(CityEnum.SAO_PAULO), Model(
            CityEnum.SILVERSTONE)
    )

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
        searchLocation(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_update -> {
                updateWeather()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun subscribeUI() {
        weatherViewModel.openWeatherModel.observe(this){
            it?.let { weather ->
                cities.forEach { model ->
                    if(weather.city.equals(model.cityEnum.description, true)){
                        model.temperature = weather.temperature
                    }
                }
                _adapter.notifyDataSetChanged()
            }
        }
        weatherViewModel.isLoading.observe(this) {
            progressLoader.setVisibility(it)
        }
        weatherViewModel.errorMessage.observe(this, { error ->
            val msg = error ?: getString(R.string.error_invalid_response)
            binding.recyclerView.snackBarIndefinite(message = msg) { snackbar ->
                snackbar.dismiss()
            }
        })
    }

    override fun getViewBinding() = ActivityWeatherBinding.inflate(layoutInflater)

    private fun searchLocation(menu: Menu?) {
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val item = menu?.findItem(R.id.action_search)
        item?.let {
            val searchView = it.actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isNotEmpty()) {
                        startActivity(ForecastActivity.newIntent(this@WeatherActivity, query))
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (searchView.query.isEmpty()) {
                        return true
                    }
                    return false
                }
            })
        }
    }
}