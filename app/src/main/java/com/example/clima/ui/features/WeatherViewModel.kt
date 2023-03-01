package com.example.clima.ui.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.clima.data.model.ForeCastModel
import com.example.clima.domain.model.ForeCastDomain
import com.example.clima.domain.model.WeatherDomain
import com.example.clima.domain.usecase.forecast.ForeCastUseCase
import com.example.clima.domain.usecase.weather.FeatchWeatherUseCase
import com.example.clima.ui.features.common.BaseViewModel
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherViewModel(
    private val weatherUseCase: FeatchWeatherUseCase,
    private val foreCastUseCase: ForeCastUseCase
) : BaseViewModel() {

    private val _openForeCastModel = MutableLiveData<List<ForeCastDomain>?>()
    val openForeCastModel: LiveData<List<ForeCastDomain>?> = _openForeCastModel

    private val _openWeatherModel = MutableLiveData<WeatherDomain?>()
    val openWeatherModel: LiveData<WeatherDomain?> = _openWeatherModel

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

    fun requestForecast(location: String) = doAsyncWork {
        foreCastUseCase.execute(location).collect {
            _openForeCastModel.postValue(it)
        }
    }
}

//    fun getForecast(location: String) {
//        viewModelScope.launch(IO) {
//            weatherRepository.getForecast(location).let {
//                _openForeCastModel.postValue(it)
//            }
//        }
//    }
