package com.example.clima.domain.usecase.weather

import com.example.clima.domain.model.WeatherResponseDomain
import com.example.clima.domain.repository.WeatherRepository
import com.example.clima.domain.usecase.UseCase

class FeatchWeatherUseCase(
    private val weatherRepository: WeatherRepository
):UseCase<String?,WeatherResponseDomain> {
    override suspend fun execute(param: String?) = weatherRepository.fetchWeather(param)

}