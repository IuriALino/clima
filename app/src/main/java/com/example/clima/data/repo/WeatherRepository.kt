package com.example.clima.data.repo

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.clima.data.model.WeatherModel
import com.example.clima.data.source.HttpResult
import com.example.clima.data.source.defaultError
import com.example.clima.data.source.defaultFailure
import com.example.clima.data.source.retrofit.client.WeatherAPIClient
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherRepository(
    private val weatherAPIClient: WeatherAPIClient,
    private val context: Context,
    //private val appPreferences: AppPreferences
) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isloading = liveData(IO) { emitSource(_isLoading) }

    suspend fun fetchWeather(
        location: String
    ) = withContext(IO) {
        _isLoading.postValue(true)
        val response = weatherAPIClient.weather(location)
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
                    return@withContext Pair(response.data.body(), error)
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
}