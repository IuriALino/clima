package com.example.clima.data.repository

import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.source.remote.RequestManager
import com.example.clima.data.source.remote.dto.ForeCastDTO
import com.example.clima.data.source.remote.mapper.weather.WeatherRemoteMapper
import com.example.clima.data.source.remote.service.WeatherAPI
import com.example.clima.data.source.room.storage.AuthStorage
import com.example.clima.domain.model.ForeCastDomain
import com.example.clima.domain.model.WeatherDomain
import com.example.clima.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherRepositoryImpl(
    private val weatherAPI: WeatherAPI,
    private val authStorage: AuthStorage
) : WeatherRepository {

    private suspend fun saveCity(it: ForeCastDTO) {
        ForeCastModel.ForeCastRemoteMapper(it).forEach { forecast ->
            authStorage.saveDataForeCast(forecast)
        }
    }

    private suspend fun deleteCity(it: ForeCastDTO) {
        ForeCastModel.ForeCastRemoteMapper(it).forEach { forecast ->
            authStorage.deleteCity(forecast.city)
        }
    }
    override suspend fun featchWeather(params: String): Flow<WeatherDomain?> = flow {
        RequestManager.requestFromApi { weatherAPI.featchWeather(params) }.run {
            emit(this?.let { WeatherRemoteMapper.toDomain(it) })
        }
    }

    override suspend fun requestForecast(params: String): Flow<List<ForeCastDomain>?> = flow {
        RequestManager.requestFromApi { weatherAPI.featchForecast(params) }?.run {
            deleteCity(this)
            saveCity(this)
            emit(ForeCastModel.ForeCastRemoteMapper(this))
        }
    }

    override suspend fun getForecast(params: String): Flow<List<ForeCastDomain>?> = flow {
        emit(authStorage.getForecast(params))
    }
}

