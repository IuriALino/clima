package com.example.clima.presentation.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.clima.domain.model.ForeCastDomain
import com.example.clima.domain.model.WeatherDomain
import com.example.clima.domain.usecase.forecast.ForeCastUseCase
import com.example.clima.domain.usecase.forecast.GetForeCastUseCase
import com.example.clima.domain.usecase.weather.FeatchWeatherUseCase
import com.example.clima.presentation.features.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherViewModel(
    private val weatherUseCase: FeatchWeatherUseCase,
    private val foreCastUseCase: ForeCastUseCase,
    private val getForeCastUseCase: GetForeCastUseCase
) : BaseViewModel() {

    private val _openForeCastModel = MutableLiveData<List<ForeCastDomain>?>()
    val openForeCastModel: LiveData<List<ForeCastDomain>?> = _openForeCastModel

    private val _openWeatherModel = MutableLiveData<WeatherDomain?>()
    val openWeatherModel: LiveData<WeatherDomain?> = _openWeatherModel

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

    fun getForeCast(location: String) = doAsyncWork {
        getForeCastUseCase.execute(location).collect {
            _openForeCastModel.postValue(it)
        }
    }
}
