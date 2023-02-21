package com.example.clima.ui

import androidx.lifecycle.*
import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.model.WeatherModel
import com.example.clima.data.repository.WeatherRepositoryimpl
import com.example.clima.domain.model.WeatherDomain
import com.example.clima.domain.usecase.weather.FeatchWeatherUseCase
import com.example.clima.ui.common.BaseViewModel
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherViewModel(
    private val weatherUseCase: FeatchWeatherUseCase
) : BaseViewModel() {

    private val _openWeatherModel = MutableLiveData<WeatherDomain?>()
    val openWeatherModel: LiveData<WeatherDomain?> = _openWeatherModel

    private val _openForeCastModel = MutableLiveData<List<ForeCastModel>?>()
    val openForeCastModel: LiveData<List<ForeCastModel>?> = _openForeCastModel

    private val _error = MutableLiveData<JsonObject>()
    val error: LiveData<JsonObject> = _error

    val errorMessage = _error.switchMap {
        liveData {
            emit(it.get("message")?.asString)
        }
    }

    fun requestWeather(location: String) = doAsyncWork {
            weatherUseCase.execute(location).collect {
                _openWeatherModel.postValue(it)
            }
        }

//    fun requestForecast(location: String) {
//        viewModelScope.launch(IO) {
//            val response = weatherRepository.fetchForeCast(location)
//            response.first?.let { _openForeCastModel.postValue(it) }
//            response.second?.let { _error.postValue(it) }
//        }
//    }

//    fun getForecast(location: String) {
//        viewModelScope.launch(IO) {
//            weatherRepository.getForecast(location).let {
//                _openForeCastModel.postValue(it)
//            }
//        }
//    }
}