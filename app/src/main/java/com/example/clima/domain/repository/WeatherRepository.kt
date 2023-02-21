package com.example.clima.domain.repository

import com.example.clima.domain.model.WeatherResponseDomain

interface WeatherRepository {
    suspend fun fetchWeather(params: String?): WeatherResponseDomain
}