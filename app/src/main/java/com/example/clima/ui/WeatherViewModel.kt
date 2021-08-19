package com.example.clima.ui

import androidx.lifecycle.*
import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.model.WeatherModel
import com.example.clima.data.repo.WeatherRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val isLoading = weatherRepository.isloading

    private val _openWeatherModel = MutableLiveData<WeatherModel?>()
    val openWeatherModel: LiveData<WeatherModel?> = _openWeatherModel

    private val _openForeCastModel = MutableLiveData<List<ForeCastModel>?>()
    val openForeCastModel: LiveData<List<ForeCastModel>?> = _openForeCastModel

    private val _error = MutableLiveData<JsonObject>()
    val error: LiveData<JsonObject> = _error

    val errorMessage = _error.switchMap {
        liveData {
            emit(it.get("message")?.asString)
        }
    }

    fun requestWeather(location : String) {
        viewModelScope.launch(IO) {
        val response = weatherRepository.fetchWeather(location)
                response.first?.let { _openWeatherModel.postValue(it) }
                response.second?.let { _error.postValue(it) }
        }
    }

    fun requestForecast(location : String) {
        viewModelScope.launch(IO) {
            val response = weatherRepository.fetchForeCast(location)
            response.first?.let { _openForeCastModel.postValue(it) }
            response.second?.let { _error.postValue(it) }
        }
    }

    fun getForecast(location: String) {
        viewModelScope.launch(IO) {
            weatherRepository.getForecast(location).let {
                _openForeCastModel.postValue(it)
            }
        }
    }
}