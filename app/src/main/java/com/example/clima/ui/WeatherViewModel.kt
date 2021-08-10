package com.example.clima.ui

import androidx.lifecycle.*
import com.example.clima.data.CityEnum
import com.example.clima.data.repo.WeatherRepository
import com.example.clima.data.source.retrofit.response.WeatherResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    //val isLoading = weatherRepository.isloading

    private val _openWeatherResponse = MutableLiveData<WeatherResponse?>()
    val openWeatherResponse: LiveData<WeatherResponse?> = _openWeatherResponse

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
                response.first?.let { _openWeatherResponse.postValue(it) }
                response.second?.let { _error.postValue(it) }
        }
    }
}