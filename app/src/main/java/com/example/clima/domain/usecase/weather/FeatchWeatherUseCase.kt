package com.example.clima.domain.usecase.weather

import com.example.clima.domain.model.WeatherDomain
import com.example.clima.domain.repository.WeatherRepository
import com.example.clima.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

class FeatchWeatherUseCase(
    private val weatherRepository: WeatherRepository
):UseCase<String, Flow<WeatherDomain?>> {
    override suspend fun execute(param: String) = weatherRepository.featchWeather(param)

}