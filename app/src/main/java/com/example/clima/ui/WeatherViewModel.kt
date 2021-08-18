package com.example.clima.ui

import androidx.lifecycle.*
import com.example.clima.data.model.CityEnum
import com.example.clima.data.model.WeatherModel
import com.example.clima.data.repo.WeatherRepository
import com.example.clima.data.source.retrofit.response.ForeCastResponse
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

    private val _openForeCastResponse = MutableLiveData<ForeCastResponse?>()
    val openForeCastResponse: LiveData<ForeCastResponse?> = _openForeCastResponse

    private val _error = MutableLiveData<JsonObject>()
    val error: LiveData<JsonObject> = _error

    val errorMessage = _error.switchMap {
        liveData {
            emit(it.get("message")?.asString)
        }
    }

    fun requestWeather(location : CityEnum) {
        viewModelScope.launch(IO) {
        val response = weatherRepository.fetchWeather("${location.description}, ${location.country}")
                response.first?.let { _openWeatherModel.postValue(it) }
                response.second?.let { _error.postValue(it) }
        }
    }

    fun requestForecast(location : String) {
        viewModelScope.launch(IO) {
            val response = weatherRepository.fetchForeCast(location)
            response.first?.let { _openForeCastResponse.postValue(it) }
            response.second?.let { _error.postValue(it) }
        }
    }
}