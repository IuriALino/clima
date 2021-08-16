package com.example.clima.data.source.retrofit.client

import com.example.clima.data.source.HttpResult
import com.example.clima.data.source.retrofit.RetrofitClient
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension

@KoinApiExtension
class WeatherAPIClient(
    private val retrofit: RetrofitClient
) {
    suspend fun weather(
        location: String
    ) = withContext(IO) {
        return@withContext try {
            HttpResult.Success(retrofit.weatherService().fetchWeather(location))
        } catch (e: Throwable) {
            HttpResult.Error(e)
        }
    }

    suspend fun forecast(
        location: String
    ) = withContext(IO) {
        return@withContext try {
            HttpResult.Success(retrofit.weatherService().fetchForecast(location))
        } catch (e: Throwable) {
            HttpResult.Error(e)
        }
    }
}