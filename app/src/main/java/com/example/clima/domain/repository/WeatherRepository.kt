package com.example.clima.domain.repository

import com.example.clima.domain.model.WeatherDomain
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun featchWeather(params: String): Flow<WeatherDomain?>
}