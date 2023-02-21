package com.example.clima.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.clima.data.model.ForeCastModel
import com.example.clima.data.model.WeatherModel
import com.example.clima.data.source.HttpResult
import com.example.clima.data.source.defaultError
import com.example.clima.data.source.defaultFailure
import com.example.clima.data.source.retrofit.client.WeatherAPIClient
import com.example.clima.data.source.remote.dto.ForeCastDTO
import com.example.clima.data.source.room.storage.AuthStorage
import com.example.clima.domain.repository.WeatherRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherRepositoryimpl(
    private val weatherAPIClient: WeatherAPIClient,
    private val context: Context,
    private val authStorage: AuthStorage
):WeatherRepository {
    private val _isLoading = MutableLiveData<Boolean>()
    val isloading = liveData(IO) { emitSource(_isLoading) }

    override suspend fun featchWeather(
        params: String
    ) = withContext(IO) {
        _isLoading.postValue(true)
        val response = weatherAPIClient.weather(params)
        var error: JsonObject? = null
        when (response) {
            is HttpResult.Success -> {
                if (response.data.isSuccessful) {
                    _isLoading.postValue(false)
                    response.data.body()?.let {
                        return@withContext Pair(WeatherModel.fromResponse(it), error)
                    }
                    return@withContext Pair(null, error)
                } else {
                    response.data.defaultFailure {
                        error = it
                    }
                    _isLoading.postValue(false)
                    return@withContext Pair(null, error)
                }
            }
            is HttpResult.Error -> {
                response.exception.defaultError {
                    error = it
                }
                _isLoading.postValue(false)
                return@withContext Pair(null, error)
            }
        }
    }

    suspend fun fetchForeCast(
        location: String
    ) = withContext(IO) {
        _isLoading.postValue(true)
        val response = weatherAPIClient.forecast(location)
        var error: JsonObject? = null
        when (response) {
            is HttpResult.Success -> {
                if (response.data.isSuccessful) {
                    _isLoading.postValue(false)
                    response.data.body()?.let {
                        deleteCity(it)
                        saveCity(it)
                        return@withContext Pair(ForeCastModel.fromResponse(it), error)
                    }
                    return@withContext Pair(null, error)
                } else {
                    response.data.defaultFailure {
                        error = it
                    }
                    _isLoading.postValue(false)
                    return@withContext Pair(null, error)
                }
            }
            is HttpResult.Error -> {
                response.exception.defaultError {
                    error = it
                }
                _isLoading.postValue(false)
                return@withContext Pair(null, error)
            }
        }
    }

    private suspend fun saveCity(it: ForeCastDTO) {
        ForeCastModel.fromResponse(it).forEach { forecast ->
            authStorage.saveDataForeCast(forecast)
        }
    }

    private suspend fun deleteCity(it: ForeCastDTO) {
        ForeCastModel.fromResponse(it).forEach { forecast ->
            authStorage.deleteCity(forecast.city)
        }
    }

    suspend fun getForecast(location: String) = withContext(IO) {
        _isLoading.postValue(true)
        val authEntity = authStorage.getForecast(location)
        _isLoading.postValue(false)
        return@withContext authEntity
    }
}